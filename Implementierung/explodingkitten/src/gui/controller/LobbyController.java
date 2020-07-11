package gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import server.LobbyInterface;
import server.Nachricht;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    public VBox roomPlayer;

    private static int spielraumNummer = 1;


    public void setName(String n) {
        this.name = n;

        try {
            LobbyObserver il = new LobbyObserver(this);
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");

            lb.registerObserver(name, il);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
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

    public void onInput(ActionEvent actionEvent) {

    }

    public void onInputText(InputMethodEvent inputMethodEvent) {

    }

    public void sendmessage(Event mouseEvent) {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");

            Timestamp tm = new Timestamp(new Date().getTime());
            lb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void zurückLobby(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent, name);
    }


    public void createRoom(ActionEvent actionEvent) {
        try {
            LobbyInterface l = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");
            //boolean check = l.addRoom(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        updateRoomList();
    }

    public void updateRoomList() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/LobbyRoom.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Label label = (Label) root.lookup("#label1");
            if (label != null) label.setText("Raum " + spielraumNummer);
            spielraumNummer++;

            //LobbyRoom lb = fxmlLoader.getController();
            //lb.setRaum(this, sr.id, sr.playerList.size(), sr.started);
            Platform.runLater(() -> this.roomList.getChildren().add(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void lobbyRoomPlayer(ActionEvent event) {

        System.out.println("Beitreten first step: " + name);
        updateRoomPlayer();
    }

    public void updateRoomPlayer() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../vue/lobbyRoomPlayer.fxml"));
            Parent finalRoot = root;
            Platform.runLater(() -> this.roomPlayer.getChildren().add(finalRoot));
            System.out.println(name);

        } catch (IOException e) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, e);
        }
        VBox.setVgrow(root, null);
    }

    public void updateMessageList() {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");
            ArrayList<Nachricht> nList = lb.getMessage();

            for (int i = lastMessage; i < nList.size(); i++) {
                Nachricht n = nList.get(i);
                FXMLLoader fxmlLoader;
                Parent root;
                if (n.sender.equals(name)) {
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
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
