package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static gui.GuiHelper.showErrorOrWarningAlert;

public class dateiAendernController {

    @FXML
    public PasswordField passwortBestätigen;
    @FXML
    public PasswordField passwort;
    @FXML
    public TextField benutzername;


    public void zurück(ActionEvent actionEvent) throws IOException{
        VueManager.goToMenü(actionEvent);
    }

    public void datenÄndern(ActionEvent actionEvent) throws IOException {
        if(passwortBestätigen.getText().isEmpty() && passwort.getText().isEmpty()){
            System.out.println("Passwort nicht verändert");
        }else{
            if (!(passwort.getText().equals(passwortBestätigen.getText()))) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", " Bitte,Geben Sie dasselbe Passwort ein");
            }else{
                showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Passwort geändert", "Passwort geändert", "Die Passwort wurde erfolgreich geändert");

            }
        }
        if(!benutzername.getText().isEmpty()){
            System.out.println("Benutzername verändert");
            showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Benutzername geändert", "Benutzername geändert", "Die Benutzername wurde erfolgreich geändert");

        }

    }

    public void accountLöschen(ActionEvent actionEvent) throws IOException {
        showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account Löschen", "Account Löschen", "Möchten Sie Ihr Konto löschen ?");


    }
}
