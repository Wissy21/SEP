package main.client.gui.controller;

import main.exceptions.UserNameAlreadyExistsException;
import main.server.datenbankmanager.DBinterface;
import main.exceptions.NotEqualPassWordException;
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

import static main.client.gui.GuiHelper.showErrorOrWarningAlert;

public class DateiAendernController {

    @FXML
    public PasswordField passwortBestaetigen;
    @FXML
    public PasswordField passwort;
    @FXML
    public TextField benutzername;

    public String name;
    public String serverIp;


    /**
     * Initilaisiert den Daten-Ändern Bildschirm
     * @param n Name des anfragenden Nutzers
     */
    public void set(String n,String ip) {
        name = n;
        this.serverIp = ip;
    }


    /**
     * Bewegt Nutzer zurück in zum Menü
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void zurueck(ActionEvent actionEvent) {
        try {
            VueManager.goToMenue(actionEvent, name,serverIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nimmt die eingegebenen Daten und lässt die Datenbank die alten Daten zu den eingegebenen neuen Daten ändern
     * Bei fehlerhaften Eingaben werden Popups erzeugt die die Fehler erklären
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void datenAendern(ActionEvent actionEvent) {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
            boolean check = db.datenAendern(name, benutzername.getText(), passwort.getText(), passwortBestaetigen.getText());

            if (check) {
                showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account bearbeitet", "Account bearbeitet", "Ihre Daten wurde aktualisiert!");
            }

        } catch (NotBoundException | SQLException | ClassNotFoundException | RemoteException | MalformedURLException e) {
            e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Benutzername vergeben", "Benutzername schon vergeben", "Ihr gewünschter Benutzername ist schon vergeben.");
        } catch (NotEqualPassWordException e) {
            showErrorOrWarningAlert(Alert.AlertType.ERROR, "Passwort Fehler", "Falsche Passwörter", "Bitte prüfen Sie, das beide Passwörter gleich sind.");
        }
    }


    /**
     * Löscht den eingeloggten Account und bewegt den Nutzer zurück zum Startbildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void accountLoeschen(ActionEvent actionEvent) {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
            boolean check = db.kontoLoeschen(name);

            if (check) {
                showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Account gelöscht", "Account gelöscht", "Ihr Konto wurde gelöscht.");
                VueManager.goToStartFenster(actionEvent,serverIp);

            }
        } catch (NotBoundException | SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
