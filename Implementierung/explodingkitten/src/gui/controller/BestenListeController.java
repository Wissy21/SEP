package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.io.IOException;

public class BestenListeController {
    public String name;
    @FXML
    public TableColumn colPosition;
    @FXML
    public TableColumn colSpieler;
    @FXML
    public TableColumn colPunkte;



    public void zurückBestenListe(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent, name);
    }

    public void setName(String n) {
        name = n;
    }
}
