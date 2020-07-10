package gui.controller;

import exceptions.RaumnameVergebenException;
import gui.GuiHelper;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.VBox;
import server.LobbyInterface;
import server.Nachricht;
import server.SpielRaum;
import server.SpielRaumInterface;
import server.datenbankmanager.DBinterface;

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

    public void setName(String n) {
        this.name = n;

        try {
            LobbyObserver il = new LobbyObserver(this);
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");

            lb.registerObserver(name, il);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        updateMessageList();
    }

    public void message(InputMethodEvent inputMethodEvent) {
        clearFields();
    }
    private void clearFields() {
        messageField.clear();
    }


    public void onInputText(InputMethodEvent inputMethodEvent) {

    }

    public void sendmessage(Event mouseEvent) {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");

            Timestamp tm = new Timestamp(new Date().getTime());
            lb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void zurückLobby(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent, name);
    }



    public void createRoom(ActionEvent actionEvent) {

        TextInputDialog dialog = new TextInputDialog("Raumname");
        dialog.setTitle("Raumname");
        dialog.setHeaderText("Bitte geben Sie den Raumnamen ein.");
        dialog.setContentText("Raumame:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            String temp = result.get();
            String raumname= temp.replace(' ','_');
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/LobbyRoom.fxml"));

            try {

                DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
                db.raumErstellen(name, raumname);

                Parent root = loader.load();
                LobbyRoom lr = loader.getController();
                lr.setName(name,raumname);
                Platform.runLater(() -> this.roomList.getChildren().add(root));
                SpielRaumInterface sb = new SpielRaum();
                Naming.rebind("rmi://localhost:1900/spielraum_"+raumname,sb);
                sb.betreten(name);
                VueManager.goToSpielraum(actionEvent,name,raumname);

            } catch (NotBoundException | ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            } catch (RaumnameVergebenException e) {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Name vergeben","Eingegebener Name vergeben","Der eingegebene Name ist vergeben.");
            }
        } else {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Keine Eingabe","Keine Eingabe","Sie haben keinen Name eingegeben.");
        }
    }

    public void updateRoomList(){
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/LobbyRoom.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Platform.runLater(() -> this.roomList.getChildren().add(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateMessageList(){
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");
            ArrayList<Nachricht> nList = lb.getMessage();

            for(int i = lastMessage; i<nList.size(); i++){
                Nachricht n = nList.get(i);
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
        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
