package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class SpielNiveauController {
    public String name;

    @FXML
    ToggleGroup spielNiveau;
    @FXML
    public RadioButton einfach;
    @FXML
    public RadioButton schwer;

    public void setName(String n){
        this.name= n;
    }

    public void okspielNiveau(ActionEvent actionEvent) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) spielNiveau.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        System.out.print(toggleGroupValue);
        // if you play with bot
        if (toggleGroupValue.equals("Einfach")) {
            //VueManager.goToSpielraum(actionEvent, name);

        } else {  // if you play with Friends
            VueManager.goToLobby(actionEvent, name);
        }
    }

    public void zurückspielNiveau(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent, name);
    }
}
