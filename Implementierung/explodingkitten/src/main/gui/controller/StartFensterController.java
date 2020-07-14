package main.gui.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartFensterController {
    public String serverIp;

    /**
     * Nutzer wird zum Registrieren Bildschirm weitergeleitet
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent, serverIp);
    }


    /**
     * Bewegt den Nutzer zum Anmeldebildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent, serverIp);
    }

    public void set(String ip) {
        this.serverIp = ip;
    }
}
