package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LobbyRoomPlayerController {
    @FXML
    public Text playerName;
    @FXML
    public VBox roomPlayer;

    public String name;

    public void setName(String n){
        this.name= n;
    }

}
