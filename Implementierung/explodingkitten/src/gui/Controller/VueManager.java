package gui.Controller;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VueManager {
    public static void goToLogIn(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/anmelden.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToRegistern(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/registrieren.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Registrieren");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
