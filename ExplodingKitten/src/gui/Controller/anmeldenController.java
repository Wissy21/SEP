package gui.Controller;

import gui.GuiHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static gui.GuiHelper.*;


public class anmeldenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;

    public void anmelden(ActionEvent actionEvent) {
        if (benutzername.getText().isEmpty() && passwort.getText().isEmpty()) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Textfields are empty","Eingabefehler","Achten Sie darauf, dass Sie die Zahlen richtig eingeben. Z.B. 100.7");
        }
        System.out.println(benutzername.getText() + " " + passwort.getText());
        clearFields();
    }

    private void clearFields() {
        benutzername.clear();
        passwort.clear();
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegistern(actionEvent);
    }
}
