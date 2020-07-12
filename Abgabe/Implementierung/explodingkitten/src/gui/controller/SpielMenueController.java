package gui.controller;


import javafx.event.ActionEvent;
import javafx.event.Event;
import server.datenbankmanager.DBinterface;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;

public class SpielMenueController {
    public String name;

    /**
     * Initialisiert die GUI des Spielmenüs
     * @param n Name des angemeldeten Benutzers
     */
    public void setName(String n){
        this.name = n;
    }

    /**
     * Bewegt den Nutzer in die Lobby
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToSpiel(ActionEvent actionEvent) throws IOException {
        VueManager.goToLobby(actionEvent, name);
    }

    /**
     * Bewegt den Nutzer zur Bestenliste
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToBestenliste(Event actionEvent) throws IOException {
        VueManager.goToBestenliste(actionEvent, name);
    }

    /**
     * Bewegt den Nutzer in den Daten ändern Bildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToDatenAendern(ActionEvent actionEvent) throws IOException {
        VueManager.datenAendern(actionEvent, name);

    }

    /**
     * Bewegt den Nutzer in den Startbildschirm
     * @param actionEvent Event das die Methode ausgelöst hat
     * @throws IOException Fehler bei Verbindung
     */
    public void goToAbmelden(ActionEvent actionEvent) throws IOException {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            db.spielerAbmelden(name);
            VueManager.goToStartFenster(actionEvent);
        } catch (NotBoundException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
