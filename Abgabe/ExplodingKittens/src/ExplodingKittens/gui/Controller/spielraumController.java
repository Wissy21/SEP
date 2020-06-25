package gui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class spielraumController {
    public ImageView send;

    public void textMessage(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }


    public void karteNehmen(MouseEvent mouseEvent) {
        VueManager.karteNehmen(mouseEvent);
    }

    public void onClickSpieler4(MouseEvent mouseEvent) throws IOException {
        VueManager.karteNehmen(mouseEvent);
    }


    public void onInput(ActionEvent actionEvent) {
    }


    public void onInputText(InputMethodEvent inputMethodEvent) {
    }

    public void sendmessage(MouseEvent mouseEvent) {
    }

    public void leereStappel(MouseEvent mouseEvent) {
    }

    public void zurückSpielraum(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }

    public void stappel(MouseEvent mouseEvent) {
    }

    public void bot(MouseEvent mouseEvent) {
    }
}
