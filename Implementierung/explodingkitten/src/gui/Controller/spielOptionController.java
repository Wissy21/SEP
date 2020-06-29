package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class spielOptionController {
    @FXML
    ToggleGroup spielOption;
    @FXML
    public RadioButton computer;
    @FXML
    public RadioButton mensch;

    public String name;

    public void okspielOption(ActionEvent actionEvent) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) spielOption.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        System.out.print(toggleGroupValue);
        // if you play with Mensch
        if (toggleGroupValue.equals("Mensch")) {
            VueManager.goToLobby(actionEvent);

        } else {  // if you play with Bot
            //add Bot -> spielraum
            VueManager.goToSpielNiveau(actionEvent);
        }
    }

    public void zurückspielOption(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent,name);
    }
}
