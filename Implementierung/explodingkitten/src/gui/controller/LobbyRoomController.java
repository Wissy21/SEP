package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyRoomController implements Initializable {

    @FXML
    public Label label1;

    public String name;

    public void setName(String n) {
        this.name = n;
    }

    public void spielraumStarten(MouseEvent mouseEvent) {
    }

    @FXML
    public void lobbyRoomPlayer(ActionEvent actionEvent) {
        System.out.println("Beitreten first step: " + name);
        new LobbyController().lobbyRoomPlayer(actionEvent);
        //updateRoomPlayer();
    }

    public void updateRoomPlayer()  {
        System.out.println("Beitreten: " + name);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../vue/lobbyRoomPlayer.fxml"));
            Text text = (Text) root.lookup("playerName");
            text.setText(name);
            new LobbyController().updateRoomPlayer();

        } catch (IOException e) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, e);
        }
        //VBox.setVgrow(root, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
