package gui.controller;

import exceptions.NotEqualPassWordException;
import exceptions.UserNameAlreadyExistsException;
import exceptions.WrongPasswordException;
import gui.GuiHelper;
import server.datenbankmanager.DBinterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import static gui.GuiHelper.showErrorOrWarningAlert;

public class RegistrierenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;
    @FXML
    public PasswordField passwortBestaetigen;


    /**
     * Nimmt die eingegebenen Daten und versucht einen Neuen Account in der Datenbank zu speichern
     * Bei Fehlern werden Popups erstellt um diese zu erklären
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void registrieren(ActionEvent actionEvent) {

        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() || passwort.getText().isEmpty() || passwortBestaetigen.getText().isEmpty() ) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Eingabefehler", "Bitte füllen Sie alle Felder aus.");
            }

            boolean check = db.spielerRegistrieren(benutzername.getText(), passwort.getText(), passwortBestaetigen.getText());
            if(check){
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.INFORMATION,"Registriert","Sie haben sich registriert","Der Account: "+benutzername.getText()+" ist jetzt registriert.");
                VueManager.goToLogIn(actionEvent);
            }
        } catch (NotBoundException | SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Benutzername Fehler", "Benutzername vergeben", "Diese Benutzername ist bereits vergeben.");
            clearFields();
        } catch (WrongPasswordException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", "Bitte geben Sie in beide Felder das selbe Passwort ein.");
            clearFields();
        } catch (NotEqualPassWordException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", "Bitte geben Sie in beide Felder das selbe Passwort ein.");
        }
    }

    /**
     * Leert die Eingabefelder
     */
    private void clearFields() {
        benutzername.clear();
        passwort.clear();
        passwortBestaetigen.clear();
    }

    /**
     * Bewegt den Nutzer zum Anmeldebildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void goToAnmelden(ActionEvent actionEvent) {
        try {
            VueManager.goToLogIn(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
