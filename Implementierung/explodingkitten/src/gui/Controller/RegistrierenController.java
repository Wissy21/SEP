package gui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrierenController {
    public TextField nickname;

    public void registrieren(ActionEvent actionEvent) {
    }

    public void goToAnmelden(ActionEvent actionEvent) throws IOException {
        VueManager.goToLogIn(actionEvent);
    }
}
