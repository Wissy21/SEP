package gui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class dateiAendernController {
    public PasswordField passwort;

    public void zurück(ActionEvent actionEvent) throws IOException{
        VueManager.goToMenü(actionEvent);
    }

    public void datenÄndern(ActionEvent actionEvent) throws IOException {
    }
}
