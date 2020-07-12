package gui.controller;

import exceptions.AccountOnlineException;
import exceptions.UserNotExistException;
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

import static gui.GuiHelper.*;


public class AnmeldenController {
    @FXML
    public TextField benutzername;
    @FXML
    public PasswordField passwort;


    /**
     * Nimmt die eingegebenen Daten und überprüft in der Datenbank, ob der Account vorhanden ist
     * Wenn ja wird der Nutzer in das Menü weitergeleitet
     * Falls es Probleme gab werden diese durch Popups erklärt
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void anmelden(ActionEvent actionEvent) {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() || passwort.getText().isEmpty()) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Eingabefehler", "Bitte füllen Sie alle Felder aus.");
            }

            db.spielerAnmelden(benutzername.getText(), passwort.getText());
            VueManager.goToMenue(actionEvent, benutzername.getText());


        } catch (NotBoundException | ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } catch (UserNotExistException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Name nicht vergeben", "Dieser Benutzername ist noch nicht vergeben.");
            clearFields();
        } catch (WrongPasswordException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", "Bitte geben Sie das korrekte Passwort ein.");
            clearFields();
        } catch (AccountOnlineException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Account Fehler", "Bereits verwendet", "Mit diesem Account ist bereits jemad anderes eingeloggt.");
        }
    }

    /**
     * Leert die Textfelder
     */
    private void clearFields() {
        benutzername.clear();
        passwort.clear();
    }

    /**
     * Nutzer wird zum Registrieren Bildschirm weitergeleitet
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void gotoRegister(ActionEvent actionEvent) {
        try {
            VueManager.goToRegister(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
