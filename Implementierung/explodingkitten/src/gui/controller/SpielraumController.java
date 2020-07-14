package gui.controller;

import exceptions.NichtGenugSpielerException;
import exceptions.NotYourRundeException;
import exceptions.SpielraumVollException;
import gui.GuiHelper;
import gui.controller.Spiel.KarteController;
import gui.controller.Spiel.OpponentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.Nachricht;
import server.SpielRaumInterface;
import server.Spieler;
import server.datenbankmanager.DBinterface;
import server.karten.Karte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SpielraumController implements IRaumObserver {
    @FXML
    public ImageView send;
    @FXML
    public HBox opponents;
    @FXML
    public AnchorPane feld;
    @FXML
    public HBox cardbox;
    @FXML
    public ScrollPane spane;
    @FXML
    TextField messageField;
    @FXML
    public VBox messageList;
    @FXML
    public ImageView ablage;

    public String name;
    public String raumname;
    public int lastMessage = 0;
    SpielRaumInterface sb;
    DBinterface db;

    public String serverIp;

    /**
     * Initiiert den Spielraum in der GUI und verbindet sich mit allen notwendigen RMI Objekten
     *
     * @param name     Name des Nutzers der GUI
     * @param raumname Name des Raums
     */
    public void setName(String name, String raumname, String serverIp) {
        this.name = name;
        this.raumname = raumname;
        cardbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        try {
            RaumObserver ro = new RaumObserver(this);
            db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
            sb = (SpielRaumInterface) Naming.lookup("rmi://" + serverIp + ":1900/spielraum_" + raumname);
            sb.registerObserver(name, ro);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
        updateMessageList();
    }

    /**
     * Wird beim drücken des Spiel starten Knopfes ausgeführt
     * Wenn das Spiel noch nicht gestartet ist wird es jetzt gestartet
     */
    public void set() {
        try {
            if (!sb.isRunning()) {
                sb.spielStarten();
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Spiel läuft bereits", "Spiel läuft bereits", "");
            }
        } catch (NichtGenugSpielerException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht genug Spieler", "Nicht genug Spieler.", "Nicht genug Spieler.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt das Spielfeld mit allen Handkarten und Informationen über die Gegner
     */
    public void createGUI() {
        try {
            int i = 1;
            for (Spieler s : sb.getSpieler()) {
                if (!s.getNickname().equals(name)) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Opponent.fxml"));
                    Parent root = loader.load();
                    OpponentController oc = loader.getController();
                    oc.set(i, s.getNickname(), 8);
                    Platform.runLater(() -> this.opponents.getChildren().add(root));
                    i++;
                } else {
                    for (Karte k : s.getHandkarte()) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Karte.fxml"));
                        Parent neuekarte = loader.load();
                        KarteController kc = loader.getController();
                        kc.setKarte(k, name, raumname);
                        Platform.runLater(() -> this.cardbox.getChildren().add(neuekarte));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Zeigt die Regeln in einem externen Fenster
     */
    public void zeigeRegeln() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Regeln.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Regeln");
        stage.setScene(new Scene(root, 2000, 755));
        stage.show();
    }


    /**
     * Sendet eine Nachricht von diesem Client an alle weiteren
     *
     * @param mouseEvent Event das die Methode auslöst
     */
    public void sendmessage(Event mouseEvent) {
        try {
            Timestamp tm = new Timestamp(new Date().getTime());
            sb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Bewegt den Nutzer zurück in die Lobby
     * Falls ein Spiel läuft wird vorher nachgefragt und dann das Spiel aufgegeben
     *
     * @param actionEvent Event das die Methode auslöst
     * @throws IOException Fehler bei GUI
     */
    public void zurueckSpielraum(Event actionEvent) throws IOException {
        if (sb.isRunning()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Spiel Verlassen?");
            alert.setHeaderText("Wollen Sie das Spiel wirklich verlassen?");
            alert.setContentText("Sie haben dann keine Chance mehr zu gewinnen.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Spieler sp : sb.getSpieler()) {
                    if (sp.getNickname().equals(name)) {
                        sb.explodiert(name, new Karte("0", "Katze1"));
                    }
                }
                try {
                    db.raumVerlassen(name, raumname);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                VueManager.goToLobby(actionEvent, name, serverIp);
            }
        } else {
            sb.spielraumVerlassen(name);
            try {
                db.raumVerlassen(name, raumname);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            VueManager.goToLobby(actionEvent, name, serverIp);
        }
    }

    /**
     * Lässt den Nutzer ein Karte ziehen
     * Wenn man nicht am Zug ist wird man darauf hingewiesen und es wird keine Karte gezogen
     *
     * @param mouseEvent Event das die Methode auslöst
     */
    public void ziehen(MouseEvent mouseEvent) {
        try {
            sb.zugBeenden(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotYourRundeException e) {
            try {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht am Zug", "Nicht dein Zug", "Es ist der Zug von " + sb.getCurrent().getNickname());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    /**
     * Fügt dem Spiel einen Bot hinzu, wenn noch Platz ist
     *
     * @param actionEvent Event das die Methode auslöst
     */
    public void bot(ActionEvent actionEvent) {
        try {
            if (!sb.isRunning()) {
                try {
                    sb.botHinzufuegen();
                    sb.sendMessage("BOT hinzugefügt", "", "Serveradmin");
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SpielraumVollException e) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Spielraum ist voll", "Spielraum ist voll", "Kein Platz mehr.");
                }
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Spiel läuft bereits", "Spiel läuft bereits", "");

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bekommt die neue Nachricht vom Server zugespielt und stellt diese dann im Chat dar, je nachdem wer sie geschickt hat
     * eigene Nachrichten rechts, andere links
     */
    @Override
    public void updateMessageList() {
        try {
            ArrayList<Nachricht> nachrichten = sb.getMessage();

            for (int i = lastMessage; i < nachrichten.size(); i++) {
                Nachricht n = nachrichten.get(i);
                FXMLLoader fxmlLoader;
                Parent root;
                if (n.sender.equals("Serveradmin")) {
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/leftMessage.fxml"));
                    root = fxmlLoader.load();
                    LeftMessageController smc = fxmlLoader.getController();

                    smc.set(n.message, n.sender, n.time);
                } else if (n.sender.equals(name)) {
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/rightMessage.fxml"));
                    root = fxmlLoader.load();
                    RightMessageController rc = fxmlLoader.getController();

                    rc.set(n.message, n.sender, n.time);
                } else {
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/leftMessage.fxml"));
                    root = fxmlLoader.load();
                    LeftMessageController lc = fxmlLoader.getController();

                    lc.set(n.message, n.sender, n.time);
                }

                Platform.runLater(() -> this.messageList.getChildren().add(root));
                lastMessage++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode die dem Client verschiedene Aufgaben geben kann, je nachdem was der Server von dem Client will/was angezeigt werden soll
     * <p>
     * startGUI : Zeige das Spielfeld
     * <p>
     * Auswahl: Wähle ein Ziel für die gespielte Wunsch Karte aus
     * <p>
     * DuBistDran: Dein Zug hat begonnen
     * <p>
     * Abgeben: Wähle eine Karte die du einem anderen Spieler wegen Wusch geben willst
     * <p>
     * AbgelegtDu: Die Karte die du gelegt hast wird aus der Hand entfernt und auf den Ablagestapel gelegt
     * <p>
     * Abgelegt: Die Karte die jemand anderes gelegt hat kommt auf den Ablagestapel
     * <p>
     * Bekommen: Du hast eine neue Karte auf die Hand bekommen
     * <p>
     * Abgegeben: Entferen die abgegebene Karte aus der Hand
     * <p>
     * Exploding: Du hast ein Exploding Kitten gezogen
     * <p>
     * Ausgeschieden: Du bist aus dem Spiel ausgeschieden
     * <p>
     * NotYourRunde: Es ist nicht deien Runde, du darfst keine Karte legen
     * <p>
     * Position: Bestimme die Position für das Exploding Kitten das du entschärft hast
     * <p>
     * Raus: Ein anderer Spieler ist ausgeschieden
     * <p>
     * BotWin: Ein Bot hat das Spiel gewonnen
     * <p>
     * Gewonnen: Du hast gewonnen, der Sieg wird in der Bestenliste erfasst und der Spielraum wird geschlossen
     * <p>
     * Verlassen: Verlasse den Spielraum
     * <p>
     * default: Durch Blick in die Zukunft hast du die ersten drei Karten des Spielstapel gesehen
     * <p>
     * Wenn man nicht angesprochen wird hört man trotzdem mit um eventuelle Änderungen an der Anzahl von Handkarten der Gegner zu erfassen
     *
     * @param spielername Spieler der benachrichtigt wird
     * @param message     Nachricht an den SPieler
     * @param k           Optionale Karte zur Nachricht hinzu
     */
    @Override
    public void notify(String spielername, String message, Karte k) {
        if (name.equals(spielername)) {
            switch (message) {

                case "startGUI":
                    Platform.runLater(() -> createGUI());
                    break;

                case "Auswahl":
                    try {
                        ArrayList<Spieler> choice = new ArrayList<>();
                        for (Spieler s : sb.getSpieler()) {
                            if (!s.getNickname().equals(name)) {
                                choice.add(s);
                            }
                        }
                        FutureTask query = new FutureTask(() -> zielAuswaehlen(choice));
                        Platform.runLater(query);

                        sb.setAusgewaehler((Spieler) query.get());
                        break;
                    } catch (RemoteException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                case "DuBistDran":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Ihr Zug", "Ihr Zug", "Es ist Ihr Zug."));
                    break;

                case "Abgeben":
                    try {
                        Spieler geber = null;
                        for (Spieler s : sb.getSpieler()) {
                            if (s.getNickname().equals(name)) {
                                geber = s;
                            }
                        }
                        ArrayList<Karte> choice = geber.getHandkarte();
                        FutureTask query = new FutureTask(() -> karteAuswaehlen(choice));
                        Platform.runLater(query);
                        sb.abgeben(name, (Karte) query.get());
                        break;
                    } catch (RemoteException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                case "AbgelegtDu":
                    for (Node n : cardbox.getChildren()) {
                        AnchorPane ap = (AnchorPane) n;
                        for (Node node : ap.getChildren()) {
                            ImageView iv = (ImageView) node;
                            if (iv.getId().equals(k.getName())) {
                                Platform.runLater(() -> cardbox.getChildren().remove(ap));
                                break;
                            }
                        }
                    }
                    Platform.runLater(() -> ablage.setImage(new Image(getClass().getResource("../images/Karten/" + k.getEffekt() + ".png").toString(), 0, 150, true, false)));
                    break;

                case "Abgelegt":
                    Platform.runLater(() -> ablage.setImage(new Image(getClass().getResource("../images/Karten/" + k.getEffekt() + ".png").toString(), 0, 150, true, false)));
                    break;


                case "Bekommen":
                    try {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Karte.fxml"));
                        Parent neuekarte = loader.load();
                        KarteController kc = loader.getController();
                        kc.setKarte(k, name, raumname);
                        Platform.runLater(() -> this.cardbox.getChildren().add(neuekarte));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case "Abgegeben":
                    for (Node n : cardbox.getChildren()) {
                        AnchorPane ap = (AnchorPane) n;
                        for (Node node : ap.getChildren()) {
                            ImageView iv = (ImageView) node;
                            if (iv.getId().equals(k.getName())) {
                                Platform.runLater(() -> cardbox.getChildren().remove(ap));
                                break;
                            }
                        }
                    }
                    break;

                case "Exploding":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Exploding Kitten", "Schnell! Entschärfen!", "Sonst haben Sie verloren!"));
                    break;

                case "Ausgeschieden":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Ausgeschieden", "Schade", "Schade! Sie sind ausgeschieden."));
                    try {
                        sb.explodiert(spielername, k);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case "NotYourRunde":
                    try {
                        GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht am Zug", "Nicht dein Zug", "Es ist der Zug von " + sb.getCurrent().getNickname());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Position":
                    try {
                        ArrayList<Integer> choice = new ArrayList<>();
                        for (int i = 0; i <= sb.getStapelSize(); i++) {
                            choice.add(i);
                        }

                        FutureTask query = new FutureTask(() -> positionAuswahl(choice));
                        Platform.runLater(query);

                        sb.setPosition((Integer) query.get());
                        break;
                    } catch (RemoteException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                case "Raus":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Explosion!", "Ein Spieler ist explodiert.", k.getEffekt() + " ist expoldiert."));
                    break;

                case "BotWin":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Vorbei!", "Ein Bot hat das Spiel gewonnen.", "Das ist schon traurig."));
                    break;

                case "Gewonnen":
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Glückwunsch!", "Sie haben das Spiel gewonnen.", "Gut gemacht."));
                    try {
                        db.siegEintragen(name);
                        Timestamp tm = new Timestamp(new Date().getTime());
                        sb.sendMessage("Spielraum geschlossen.\n Bitte verlassen Sie den Spielraum.", tm.toString(), "Serveradmin");
                        sb.spielraumVerlassen(name);
                        db.raumVerlassen(name, raumname);
                        sb.exit();
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Verlassen":
                    Platform.runLater(() -> {
                        try {
                            VueManager.goToLobby(new Event(feld, feld, EventType.ROOT), name, serverIp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                default:
                    Platform.runLater(() -> GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Blick in die Zukunft", "Die nächsten Kartten sind", message));
                    break;
            }
        } else {
            if ("AbgelegtDu".equals(message)) {
                Platform.runLater(() -> updateOpponent(spielername, -1));
            } else if ("Bekommen".equals(message)) {
                Platform.runLater(() -> updateOpponent(spielername, 1));
            } else if ("Abgegeben".equals(message)) {
                Platform.runLater(() -> updateOpponent(spielername, -1));
            }
        }
    }

    /**
     * Erstellt einen Auswahldialog um das Ziel einer Wunsch Karte zu bestimmen
     *
     * @param choice Auswahlmöglichkeiten für das Ziel des Wunsches
     * @return Auswahl des Spielers, von wem er die Karte will
     */
    public Spieler zielAuswaehlen(ArrayList<Spieler> choice) {
        ChoiceDialog<Spieler> dialog = new ChoiceDialog<>(choice.get(0), choice);
        Optional<Spieler> result = dialog.showAndWait();
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                if (result.isEmpty()) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Bitte etwas auswählen", "Bitte etwas auswählen", "Sie müssen einen anderen Spieler auswählen.");
                    dialogEvent.consume();
                }
            }
        });
        return result.orElseGet(() -> choice.get(0));

    }


    /**
     * Erstellt einen Auswahldialog um die Karte zu bestimmen, die einem anderen Spieler mit Wunsch gegeben werden soll
     *
     * @param choice Auswahlmöglichkeiten für die Karte
     * @return Auswahl der Karte, die abgegben wird
     */
    public Karte karteAuswaehlen(ArrayList<Karte> choice) {

        ChoiceDialog<Karte> dialog = new ChoiceDialog<>(choice.get(0), choice);
        Optional<Karte> result = dialog.showAndWait();
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                if (result.isEmpty()) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Bitte etwas auswählen", "Bitte etwas auswählen", "Sie müssen eine Karte auswählen.");
                    dialogEvent.consume();
                }
            }
        });
        return result.orElseGet(() -> choice.get(0));
    }

    /**
     * Erstellt einen Auswahldialog um den Platz  zu bestimmen, an den das Exploding Kitten in den Spielstapel gelegt werden soll
     *
     * @param choice Auswahlmöglichkeiten für den Platz
     * @return Auswahl des Platzes
     */
    public int positionAuswahl(ArrayList<Integer> choice) {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(0, choice);

        Optional<Integer> result = dialog.showAndWait();
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                if (result.isEmpty()) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Bitte etwas auswählen", "Bitte etwas auswählen", "Sie müssen eine Position auswählen.");
                    dialogEvent.consume();
                }
            }
        });
        return result.orElseGet(() -> choice.get(0));
    }

    /**
     * Updatet die Anzeige des Gegners, nachdem er eine Karte gespielt oder Aufgenommen hat
     *
     * @param spielername Name des Gegners der betroffen ist
     * @param value       +1 wenn Karte genommen, -1 wenn Karte abgelegt
     */
    public void updateOpponent(String spielername, int value) {
        for (Node n : opponents.getChildren()) {
            AnchorPane ap = (AnchorPane) n;
            for (Node node : ap.getChildren()) {
                VBox op = (VBox) node;
                boolean richtig = false;
                for (Node no : op.getChildren()) {
                    if (no.getId().equals("name")) {
                        Text t = (Text) no;
                        if (t.getText().equals(spielername)) {
                            richtig = true;
                        }
                    }
                }
                if (richtig) {
                    for (Node no : op.getChildren()) {
                        if (no.getId().equals("anzahlk")) {
                            Text t = (Text) no;
                            String anzeige = t.getText();
                            String[] res = anzeige.split(":");
                            int zahl = Integer.parseInt(res[1]);
                            zahl += value;
                            int finalZahl = zahl;
                            t.setText(res[0] + ":" + finalZahl);
                        }
                    }
                }
            }
        }
    }

    /**
     * Methode die die Kartenleiste seitlich mit dem Scrollrad scrollen lässt
     *
     * @param scrollEvent Event das die Methode auslöst
     */
    public void scrollh(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaX() == 0 && scrollEvent.getDeltaY() != 0) {
            spane.setHvalue(spane.getHvalue() - scrollEvent.getDeltaY() / cardbox.getWidth());
        }

    }
}
