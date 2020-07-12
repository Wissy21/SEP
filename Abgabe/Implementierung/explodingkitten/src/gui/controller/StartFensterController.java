package gui.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartFensterController {


    /**
     * Nutzer wird zum Registrieren Bildschirm weitergeleitet
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent);
    }


    /**
     * Bewegt den Nutzer zum Anmeldebildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }
}
