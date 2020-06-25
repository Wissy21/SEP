package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class spielNiveauController {
    @FXML
    ToggleGroup spielNiveau;
    @FXML
    public RadioButton einfach;
    @FXML
    public RadioButton schwer;

    public void okspielNiveau(ActionEvent actionEvent) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) spielNiveau.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        // if you play with bot
        if (false) {
            VueManager.goToSpielraum(actionEvent);

        } else {  // if you play with Friends
            VueManager.goToLobby(actionEvent);
        }
    }

    public void zurückspielNiveau(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent);
    }
}
