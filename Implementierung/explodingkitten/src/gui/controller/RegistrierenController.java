package gui.controller;

import exceptions.DoppelterEintragException;
import exceptions.UserNameAlreadyExistsException;
import exceptions.WrongPasswordException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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


    //getMeldung("Dieser Benutzername ist bereits vergeben");
//VueManager.goToRegistrieren(event);

    public void registrieren(ActionEvent actionEvent) throws IOException, DoppelterEintragException {

        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");

            if (benutzername.getText().isEmpty() && passwort.getText().isEmpty() && passwortBestätigen.getText().isEmpty() ) {
                showErrorOrWarningAlert(Alert.AlertType.WARNING, "Eingabe Fehler", "Eingabefehler", " Bitte,Geben Sie Ihre Anmeldedaten ein");
            }

            boolean check = db.spielerRegistrieren(benutzername.getText(), passwort.getText(), passwortBestätigen.getText());
            if(check){
                showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Erfolgreich Registrierung", "Erfolgreich Registrierung", " Schön ,Sie sind elforgreich registriert.");
                VueManager.goToLogIn(actionEvent);
            }
        } catch (NotBoundException e) {
            //e.printStackTrace();
        } catch (UserNameAlreadyExistsException e) {
            e.printStackTrace();
        } catch (WrongPasswordException e) {
            //e.printStackTrace();
            showErrorOrWarningAlert(Alert.AlertType.WARNING, "Passwort Fehler", "Passwort ungleich", " Bitte,Geben Sie dasselbe Passwort ein");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }


    public void fromPassword2Reg(KeyEvent keyEvent) {
        passwortBestätigen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    try {
                        VueManager.goToLogIn(keyEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
