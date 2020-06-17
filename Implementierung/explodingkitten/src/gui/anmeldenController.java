package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class anmeldenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;

    public void anmelden(ActionEvent actionEvent) {
        System.out.println(benutzername.getText() + " " + passwort.getText());
        clearFields();
    }

    private void clearFields() {
        benutzername.clear();
        passwort.clear();
    }
}
