package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

public class spielOptionController {
    @FXML
    public RadioButton computer;
    @FXML
    public RadioButton mensch;

    public void okspielOption(ActionEvent actionEvent) throws IOException {
        // if you play with bot
        if (true) {
            VueManager.goToSpielNiveau(actionEvent);

        } else {  // if you play with Friends
            VueManager.goToLobby(actionEvent);
        }
    }

    public void zurückspielOption(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }
}
