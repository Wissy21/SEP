package gui.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;

import java.io.IOException;

public class SpielMenueController {
    public String name;

    public void setName(String n){
        this.name = n;
    }

    public void goToSpiel(ActionEvent actionEvent) throws IOException {
        VueManager.goToLobby(actionEvent, name);

        //VueManager.goToSpiel(actionEvent, name);
    }

    public void goToBestenliste(Event actionEvent) throws IOException {
        VueManager.goToBestenliste(actionEvent, name);
    }

    public void goToDatenÄndern(ActionEvent actionEvent) throws IOException {
        VueManager.datenAendern(actionEvent, name);

    }

    public void goToAbmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToStartFenster(actionEvent);

    }
}
