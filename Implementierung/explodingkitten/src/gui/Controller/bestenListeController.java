package gui.Controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class bestenListeController {
    public String name;

    public void zurückBestenListe(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent, name);
    }
}
