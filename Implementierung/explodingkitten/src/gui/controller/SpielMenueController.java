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

    public void setName(String n){
        this.name = n;
    }

    public void goToSpiel(ActionEvent actionEvent) throws IOException {
        VueManager.goToLobby(actionEvent, name);
    }

    public void goToBestenliste(Event actionEvent) throws IOException {
        VueManager.goToBestenliste(actionEvent, name);
    }

    public void goToDatenAendern(ActionEvent actionEvent) throws IOException {
        VueManager.datenAendern(actionEvent, name);

    }

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
