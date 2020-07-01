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

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Benutzername schon definiert", "Benutzername schon definiert", "Ihr Benutzername ist schon definiert");
        } catch (WrongPasswordException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Falsche Passwort", "Falsche Passwort", "Ihr Passwort ist Falsch");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NotEqualPassWordException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Account bearbeitet", "Account bearbeitet", "Bitte prüfen Sie Ihre Passwörte ein");
        }
    }


    public void accountLoeschen(ActionEvent actionEvent) throws IOException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            boolean check = db.kontoLoeschen(name);

            if (check) {
                System.out.println(benutzername.getText());
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotExistException e) {
            e.printStackTrace();
        }
        showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account Löschen", "Account Löschen", "Möchten Sie Ihr Konto löschen ?");
        VueManager.goToStartFenster(actionEvent);
    }
}
