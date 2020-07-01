package gui.controller;

import exceptions.UserNameAlreadyExistsException;
import exceptions.UserNotExistException;
import exceptions.WrongPasswordException;
import server.datenbankmanager.DBinterface;
import server.datenbankmanager.NotEqualPassWordException;
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

public class DateiAendernController {

    @FXML
    public PasswordField passwortBestätigen;
    @FXML
    public PasswordField passwort;
    @FXML
    public TextField benutzername;

    public String name;

    public void set(String n) {
        name = n;
    }


    public void zurück(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent, name);
    }

    public void datenAendern(ActionEvent actionEvent) throws IOException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            System.out.println(benutzername.getText() + "-----" + passwort.getText() + "-----" + passwortBestätigen.getText());
            System.out.println(name + " : name");
            boolean check = db.datenAendern(name, benutzername.getText(), passwort.getText(), passwortBestätigen.getText());

            if (check) {
                System.out.println(benutzername.getText() + "-----" + passwort.getText() + "-----" + passwortBestätigen.getText());
                showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account bearbeitet", "Account bearbeitet", "Ihre Daten wurde aktualisiert!");
            }

        } catch (NotBoundException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Benutzername vergeben", "Benutzername schon vergeben", "Ihr gewünschter Benutzername ist schon vergeben.");
        } catch (NotEqualPassWordException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Passwort Fehler", "Falsche Passwörter", "Bitte prüfen Sie, das beide Passwörter glech sind.");
        }
    }


    public void accountLoeschen(ActionEvent actionEvent) throws IOException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            boolean check = db.kontoLoeschen(name);

            if (check) {
                System.out.println(benutzername.getText());
            }
        } catch (NotBoundException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account gelöscht", "Account gelöscht", "Ihr Konto wurde gelöscht.");
        VueManager.goToStartFenster(actionEvent);
    }
}
