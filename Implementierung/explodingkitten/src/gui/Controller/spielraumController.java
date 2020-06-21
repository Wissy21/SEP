package gui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class spielraumController {
    public void goToMenu(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }

    public void onClickSpieler3(MouseEvent mouseEvent) throws IOException {
        VueManager.karteNehmen(mouseEvent);
    }

    public void onClickSpieler2(MouseEvent mouseEvent) throws IOException {
        VueManager.karteNehmen(mouseEvent);
    }

    public void textMessage(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenü(actionEvent);
    }

    public void onClickSpieler1(MouseEvent mouseEvent) throws IOException {
        VueManager.karteNehmen(mouseEvent);
    }

    public void karteNehmen(MouseEvent mouseEvent) {
        VueManager.karteNehmen(mouseEvent);
    }

    public void onClickSpieler4(MouseEvent mouseEvent) throws IOException {
        VueManager.karteNehmen(mouseEvent);
    }


}
