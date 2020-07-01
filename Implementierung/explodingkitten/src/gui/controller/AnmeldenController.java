package gui.controller;

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


    public void anmelden(ActionEvent actionEvent) throws IOException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() || passwort.getText().isEmpty()) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Eingabefehler", "Bitte füllen Sie alle Felder aus.");
            }

            boolean check = db.spielerAnmelden(benutzername.getText(), passwort.getText());

            if (check) {
                VueManager.goToMenue(actionEvent, benutzername.getText());
            }

        } catch (NotBoundException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (UserNotExistException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabefehler", "Name nicht vergeben", "Dieser Benutzername ist noch nicht vergeben.");
            clearFields();
        } catch (WrongPasswordException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", "Bitte geben Sie das korrekte Passwort ein.");
            clearFields();
        }
    }

    private void clearFields() {
        benutzername.clear();
        passwort.clear();
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent);
    }
}
