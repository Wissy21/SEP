package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SpielraumController {
    @FXML
    public ImageView send;
    public String name;

    public void setName(String n){
        this.name = n;
    }


    /*public void karteNehmen(MouseEvent mouseEvent) {
        VueManager.karteNehmen(mouseEvent);
    }*/

    public void onInput(ActionEvent actionEvent) {
    }


    public void onInputText(InputMethodEvent inputMethodEvent) {
    }

    public void sendmessage(MouseEvent mouseEvent) {
    }

    public void leereStappel(MouseEvent mouseEvent) {
    }

    public void zurückSpielraum(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent, name);
    }

    public void stappel(MouseEvent mouseEvent) {
    }

    public void bot(MouseEvent mouseEvent) {
    }
}