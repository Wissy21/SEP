package gui.controller;

import exceptions.NameFalschException;
import exceptions.PasswortFalschException;
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


    public void anmelden(ActionEvent actionEvent) throws IOException, NameFalschException, PasswortFalschException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() && passwort.getText().isEmpty()) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Textfields are empty", "Eingabefehler", " Bitte,Geben Sie Ihre Anmeldedaten ein");
            }

            boolean check = db.spielerAnmelden(benutzername.getText(), passwort.getText());

            if (check) {
                System.out.println(benutzername.getText() + " " + passwort.getText());
                VueManager.goToMenue(actionEvent, benutzername.getText());
            }

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (UserNotExistException e) {
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Textfields are empty", "Eingabefehler", " Bitte,Geben Sie Ihre Anmeldedaten ein");
            //e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (WrongPasswordException e) {
            e.printStackTrace();
        }
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
