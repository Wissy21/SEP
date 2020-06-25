package gui.Controller;

import Exceptions.NameFalschException;
import Exceptions.PasswortFalschException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


    public void anmelden(ActionEvent actionEvent) throws  IOException , NameFalschException, PasswortFalschException {
        if (benutzername.getText().isEmpty() && passwort.getText().isEmpty()) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Textfields are empty","Eingabefehler"," Bitte,Geben Sie Ihre Anmeldedaten ein");
        }
        System.out.println(benutzername.getText() + " " + passwort.getText());
        VueManager.goToMenü(actionEvent);
    }

    private void clearFields() {
        benutzername.clear();
        passwort.clear();
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent);
    }

    /*public void gotoMenü(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }*/
}
