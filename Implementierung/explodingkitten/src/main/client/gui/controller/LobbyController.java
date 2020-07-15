package main.client.gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.client.SpielenClient;
import main.exceptions.RaumnameVergebenException;
import main.exceptions.SpielLauftBereitsException;
import main.exceptions.SpielraumVollException;
import main.client.gui.GuiHelper;
import main.server.LobbyInterface;
import main.server.Nachricht;
import main.server.SpielRaum;
import main.server.SpielRaumInterface;
import main.server.datenbankmanager.DBinterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class LobbyController implements ILobbyObserver {
    public String name;
    public int lastMessage = 0;

    @FXML
    public TextField messageField;

    @FXML
    public VBox roomList;
    @FXML
    public ImageView send;
    @FXML
    public VBox messageList;

    public String serverIp;
    /**
     * Initialisert die Lobby GUI
     *
     * @param n Name des eingeloggten Benutzers
     */
    public void setName(String n,String ip) {
        this.serverIp = ip;
        this.name = n;

        try {
            LobbyObserver il = new LobbyObserver(this);
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://" + serverIp + ":1900/lobby");

            lb.registerObserver(name, il);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        updateMessageList();
        updateRaumList();
    }

    /**
     * Sendet die eingetippte Nachricht an alle anderen Nutzer in der Lobby
     *
     * @param mouseEvent Event das die Methode ausgelöst hat
     */
    public void sendmessage(Event mouseEvent) {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://" + serverIp + ":1900/lobby");

            Timestamp tm = new Timestamp(new Date().getTime());
            lb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bewegt den Nutzer zurück zum Menü Bildschirm
     *
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void zurueckLobby(ActionEvent actionEvent) {
        try {
            VueManager.goToMenue(actionEvent, name,serverIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Lässt eine Nutzer einen neuen Raum erstellen, den er selbst benennen kann
     * Bei auftretenden Fehlern werden Popups erstellt
     *
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void createRoom(ActionEvent actionEvent) {

        TextInputDialog dialog = new TextInputDialog("Raumname");
        dialog.setTitle("Raumname");
        dialog.setHeaderText("Bitte geben Sie den Raumnamen ein.");
        dialog.setContentText("Raumname:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String temp = result.get();
            String raumname = temp.replace(' ', '_');
            FXMLLoader loader = new FXMLLoader(SpielenClient.class.getResource("gui/vue/lobbyRoom.fxml"));

            try {

                DBinterface db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
                db.raumErstellen(name, raumname);

                Parent root = loader.load();
                LobbyRoom lr = loader.getController();
                lr.setName(name, raumname, serverIp);
                Platform.runLater(() -> this.roomList.getChildren().add(root));
                SpielRaumInterface sb = new SpielRaum();
                Naming.rebind("rmi://" + serverIp + ":1900/spielraum_" + raumname, sb);
                LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://" + serverIp + ":1900/lobby");
                lb.addroom(raumname);
                sb.betreten(name);
                VueManager.goToSpielraum(actionEvent, name, raumname,serverIp);

            } catch (NotBoundException | ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            } catch (RaumnameVergebenException e) {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Name vergeben", "Eingegebener Name vergeben", "Der eingegebene Name ist vergeben.");
            } catch (SpielLauftBereitsException e) {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Spiel läuft", "Spiel läuft schon", "Der Raum hat das Spiel bereits begonnen.");
            } catch (SpielraumVollException e) {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Spielraum schon voll", "Spielraum schon voll", "Der Spielraum ist schon voll!");
            }
        } else {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Keine Eingabe", "Keine Eingabe", "Sie haben keinen Name eingegeben.");
        }
    }


    /**
     * Frischt die Nachrichtenlsite auf, sodass alle Nachrichten angezeigt werden
     */
    public void updateMessageList() {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://" + serverIp + ":1900/lobby");
            ArrayList<Nachricht> nList = lb.getMessage();

            for (int i = lastMessage; i < nList.size(); i++) {
                Nachricht n = nList.get(i);
                FXMLLoader fxmlLoader;
                Parent root;
                if (n.sender.equals(name)) {
                    fxmlLoader = new FXMLLoader(SpielenClient.class.getResource("gui/vue/rightMessage.fxml"));
                    root = fxmlLoader.load();
                    RightMessageController rc = fxmlLoader.getController();

                    rc.set(n.message, n.sender, n.time);
                } else {
                    fxmlLoader = new FXMLLoader(SpielenClient.class.getResource("gui/vue/leftMessage.fxml"));
                    root = fxmlLoader.load();
                    LeftMessageController lc = fxmlLoader.getController();

                    lc.set(n.message, n.sender, n.time);
                }

                Platform.runLater(() -> this.messageList.getChildren().add(root));
                lastMessage++;
            }
        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Frischt die Raumliste auf, sodass alle offenen Räume angezeigt werden
     * TODO auf Fehler überprüfen
     */
    @Override
    public void updateRaumList() {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://" + serverIp + ":1900/lobby");
            ArrayList<String> roomlist = lb.getRooms();
            for (int i = 0; i < roomlist.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(SpielenClient.class.getResource("gui/vue/lobbyRoom.fxml"));
                Parent root = fxmlLoader.load();
                LobbyRoom lr = fxmlLoader.getController();
                lr.setName(name, roomlist.get(i), serverIp);
                Platform.runLater(() -> this.roomList.getChildren().add(root));
            }
        } catch (UnknownHostException e){

        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }

    }
}
