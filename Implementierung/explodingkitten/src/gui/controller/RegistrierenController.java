package gui.controller;

import exceptions.UserNameAlreadyExistsException;
import exceptions.WrongPasswordException;
import server.datenbankmanager.DBinterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;

import static gui.GuiHelper.showErrorOrWarningAlert;

public class RegistrierenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;
    @FXML
    public PasswordField passwortBestätigen;


    public void registrieren(ActionEvent actionEvent) throws IOException {

        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() || passwort.getText().isEmpty() || passwortBestätigen.getText().isEmpty() ) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Eingabefehler", "Bitte füllen Sie alle Felder aus.");
            }

            boolean check = db.spielerRegistrieren(benutzername.getText(), passwort.getText(), passwortBestätigen.getText());
            if(check){
                VueManager.goToLogIn(actionEvent);
            }
        } catch (NotBoundException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Benutzername Fehler", "Benutzername vergeben", "Diese Benutzername ist bereits vergeben.");
            clearFields();
        } catch (WrongPasswordException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", "Bitte geben Sie in beide Felder das selbe Passwort ein.");
            clearFields();
        }
    }

    private void clearFields() {
        benutzername.clear();
        passwort.clear();
        passwortBestätigen.clear();
    }
    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }


}
