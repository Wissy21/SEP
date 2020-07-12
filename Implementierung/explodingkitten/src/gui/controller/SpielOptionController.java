package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class SpielOptionController {

    @FXML
    ToggleGroup spielOption;
    @FXML
    public RadioButton computer;
    @FXML
    public RadioButton mensch;

    public String name;

    public void setName(String n){
        this.name= n;
    }

    public void okspielOption(ActionEvent actionEvent) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) spielOption.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        System.out.print(toggleGroupValue);
        // if you play with Mensch
        if (toggleGroupValue.equals("Mensch")) {
            VueManager.goToLobby(actionEvent, name);

        } else {  // if you play with Bot
            //add Bot -> spielraum
            VueManager.goToSpielraum(actionEvent, name);
        }
    }

    public void zurückspielOption(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent,name);
    }
}
