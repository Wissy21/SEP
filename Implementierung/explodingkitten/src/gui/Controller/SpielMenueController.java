package gui.Controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SpielMenueController {
    public String name;

    public void setName(String n){
        this.name = n;
    }

    public void goToSpiel(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent);
    }

    public void goToBestenliste(ActionEvent actionEvent) throws IOException {
        VueManager.goToBestenliste(actionEvent);
    }

    public void goToDatenÄndern(ActionEvent actionEvent) throws IOException {
        VueManager.datenÄndern(actionEvent);

    }

    public void goToAbmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToStartFenster(actionEvent);

    }
}
