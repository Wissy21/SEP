package gui.Controller;

import Exceptions.DoppelterEintragException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;

import static gui.GuiHelper.showErrorOrWarningAlert;

public class RegistrierenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;
    @FXML
    public PasswordField passwortBestätigen;


    //getMeldung("Dieser Benutzername ist bereits vergeben");
//VueManager.goToRegistrieren(event);

    public void registrieren(ActionEvent actionEvent) throws IOException, DoppelterEintragException {
        if (benutzername.getText().isEmpty() && passwort.getText().isEmpty() && passwortBestätigen.getText().isEmpty() ) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabe Fehler", "Eingabefehler", " Bitte,Geben Sie Ihre Anmeldedaten ein");
        }
        if (!(passwort.getText().equals(passwortBestätigen.getText()))) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", " Bitte,Geben Sie dasselbe Passwort ein");
        }
        showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Erfolgreiche Registrierung","Erfolgreiche Registrierung"," Sie Haben Sie sich Erfolgreich Registriert");

    }

    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }


}
