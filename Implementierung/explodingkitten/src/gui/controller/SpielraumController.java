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

public class SpielraumController implements IRaumObserver{
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
    public  ImageView ablage;

    public String name;
    public String raumname;
    public int lastMessage = 0;
    SpielRaumInterface sb;
    DBinterface db;

    public void setName(String name, String raumname){
        this.name = name;
        this.raumname = raumname;
        cardbox.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));

        try {
            RaumObserver ro = new RaumObserver(this);
            db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            sb = (SpielRaumInterface) Naming.lookup("rmi://localhost:1900/spielraum_"+raumname);
            sb.registerObserver(name,ro);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
            updateMessageList();
    }

    public void set(){
        try {
            if(!sb.isRunning()) {
                try {
                    sb.spielStarten();
                    Thread.sleep(2000);
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
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (NichtGenugSpielerException e) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht genug Spieler", "Es sind noch nicht genug Spieler in dem Spielraum.", "Warten Sie auf mehr Spieler oder fügen Sie einen Bot hinzu.");
                }
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spiel läuft bereits","Spiel läuft bereits","");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


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
        stage.setScene(new Scene(root,2000,755));
        stage.show();
    }


    public void sendmessage(Event mouseEvent) {
        try {
            Timestamp tm = new Timestamp(new Date().getTime());
            sb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void zurueckSpielraum(Event actionEvent) throws IOException {
        if(sb.isRunning()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Spiel Verlassen?");
            alert.setHeaderText("Wollen Sie das Spiel wirklich verlassen?");
            alert.setContentText("Sie haben dann keine Chance mehr zu gewinnen.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                for(Spieler sp : sb.getSpieler()) {
                    if(sp.getNickname().equals(name)) {
                        sb.explodiert(name);
                    }
                }
                try {
                    db.raumVerlassen(name,raumname);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                VueManager.goToLobby(actionEvent, name);
            }
        } else {
            sb.spielraumVerlassen(name);
            try {
                db.raumVerlassen(name,raumname);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            VueManager.goToLobby(actionEvent, name);
        }
    }

    public void ziehen(MouseEvent mouseEvent) {
        try {
            sb.zugBeenden(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotYourRundeException e) {
            try {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht am Zug","Nicht dein Zug","Es ist der Zug von "+sb.getCurrent().getNickname());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    public void bot(ActionEvent actionEvent) {
        try {
            if(!sb.isRunning()) {
                try {
                    sb.botHinzufuegen();
                    Timestamp tm = new Timestamp(new Date().getTime());
                    sb.sendMessage("BOT hinzugefügt",tm.toString(),"Serveradmin");
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SpielraumVollException e) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spielraum ist voll","Spielraum ist voll","Kein Platz mehr.");
                }
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spiel läuft bereits","Spiel läuft bereits","");

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMessageList(){
        try {
            ArrayList<Nachricht> nachrichten = sb.getMessage();

            for(int i = lastMessage; i<nachrichten.size(); i++){
                Nachricht n = nachrichten.get(i);
                FXMLLoader fxmlLoader;
                Parent root;
                if(n.sender.equals(name)) {
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

    @Override
    public void notify(String spielername,String message,Karte k) {
        if(name.equals(spielername)) {
            switch (message) {

                case "Auswahl":
                    try {
                        ArrayList<Spieler> choice = new ArrayList<>();
                        for(Spieler s: sb.getSpieler()) {
                            if(!s.getNickname().equals(name)) {
                                choice.add(s);
                            }
                        }
                        Platform.runLater(()->GuiHelper.showSpielerSelect(choice));
                        sb.setAusgewaehler(zielAuswaehlen());
                        break;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                case "DuBistDran":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Ihr Zug","Ihr Zug","Es ist Ihr Zug."));
                    break;

                case "Abgeben":
                        Platform.runLater(()-> {
                            try {
                                sb.abgeben(name,karteAuswaehlen());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        });
                        break;

                case "AbgelegtDu":
                        for(Node n : cardbox.getChildren()) {
                        AnchorPane ap = (AnchorPane) n;
                        for(Node node : ap.getChildren()) {
                            ImageView iv = (ImageView) node;
                            if (iv.getId().equals(k.getName())) {
                                Platform.runLater(() -> cardbox.getChildren().remove(ap));
                                break;
                            }
                        }
                    }

                    Platform.runLater(()->ablage.setImage(new Image(getClass().getResource("../images/Karten/"+k.getEffekt()+".png").toString(),0,150,true,false)));
                    break;

                case "Abgelegt":
                    Platform.runLater(()->ablage.setImage(new Image(getClass().getResource("../images/Karten/"+k.getEffekt()+".png").toString(),0,150,true,false)));
                    Platform.runLater(()->updateOpponent(spielername));
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
                    Platform.runLater(()->cardbox.getChildren().removeIf(n -> n.getId().equals(k.getName())));
                    break;

                case "Exploding":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Exploding Kitten","Schnell! Entschärfen!","Sonst haben Sie verloren!"));
                    break;

                case "Ausgeschieden":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Ausgeschieden","Schade","Schade! Sie sind ausgeschieden."));
                    try {
                        sb.explodiert(spielername);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case "NotYourRunde":
                    try {
                        GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht am Zug","Nicht dein Zug","Es ist der Zug von "+sb.getCurrent().getNickname());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Position":
                    try {
                        ArrayList<Integer> choice = new ArrayList<>();
                        for (int i=0;i<=sb.getStapelSize();i++) {
                            choice.add(i);
                        }
                        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(0,choice);
                        dialog.setOnCloseRequest(e->Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Bitte etwas auswählen","Bitte etwas auswählen","Sie müssen einen anderen Spieler auswählen.")));
                        Optional<Integer> result = dialog.showAndWait();
                        sb.setPosition(result.get());
                        break;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                case "Raus":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION,"Explosion!","Ein Spieler ist explodiert.",k.getEffekt()+" ist expoldiert."));
                    break;

                case "BotWin":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION,"Vorbei!","Ein Bot hat das Spiel gewonnen.","Das ist schon traurig."));
                    break;

                case "Gewonnen":
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION,"Glückwunsch!","Sie haben das Spiel gewonnen.","Gut gemacht."));
                    try {
                        db.siegEintragen(name);
                    } catch (RemoteException | SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;


                    //TODO zusätzliche Nachrichten

                default:
                    Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Blick in die Zukunft","Die nächsten Kartten sind",message));
                    break;
            }
        }
    }

    public Spieler zielAuswaehlen() throws RemoteException {
        ArrayList<Spieler> choice = new ArrayList<>();
        for(Spieler s: sb.getSpieler()) {
            if(!s.getNickname().equals(name)) {
                choice.add(s);
            }
        }
        ChoiceDialog<Spieler> dialog = new ChoiceDialog<>(choice.get(0),choice);
        dialog.setOnCloseRequest(e->{Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Bitte etwas auswählen","Bitte etwas auswählen","Sie müssen einen anderen Spieler auswählen."));});
        Optional<Spieler> result = dialog.showAndWait();
        return result.get();

    }


    public Karte karteAuswaehlen() throws RemoteException {
        Spieler geber = null;
        for(Spieler s : sb.getSpieler()) {
            if(s.getNickname().equals(name)) {
                geber = s;
            }
        }
        ArrayList<Karte> choice = geber.getHandkarte();
        ChoiceDialog<Karte> dialog = new ChoiceDialog<>(choice.get(0),choice);
        dialog.setOnCloseRequest(e->{Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Bitte etwas auswählen","Bitte etwas auswählen","Sie müssen eine Karte auswählen."));});
        Optional<Karte> result = dialog.showAndWait();
        return result.get();
    }

    public void updateOpponent(String spielername) {
        for(Node n :opponents.getChildren()){
            AnchorPane ap =(AnchorPane) n;
            for(Node node : ap.getChildren()) {
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
                            zahl++;
                            int finalZahl = zahl;
                            t.setText(res[0] + finalZahl);
                        }
                    }
                }
            }
        }
    }

    public void scroll(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaX() == 0 && scrollEvent.getDeltaY() != 0) {
            spane.setHvalue(spane.getHvalue() - scrollEvent.getDeltaY() / cardbox.getWidth());
        }

    }
}
