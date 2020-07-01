package gui.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class BestenListeController {
    public String name;

    public void zurückBestenListe(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent, name);
    }

    public void setName(String n) {
        name = n;
    }
}
