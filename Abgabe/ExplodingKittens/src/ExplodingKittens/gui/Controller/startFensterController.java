package gui.Controller;

import Exceptions.DoppelterEintragException;
import javafx.event.ActionEvent;

import java.io.IOException;

public class startFensterController {
    public void satrtFenster(ActionEvent actionEvent) {

    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        VueManager.goToRegister(actionEvent);
    }
    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }
}
