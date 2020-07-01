package gui.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartFensterController {
    public void startFenster(ActionEvent actionEvent) {
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent);
    }
    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }
}
