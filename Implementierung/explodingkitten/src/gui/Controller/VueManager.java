package gui.Controller;

import javafx.event.ActionEvent;
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToRegister(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/registrieren.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Registrieren");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToMenü(Event event, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/spielmenue.fxml"));
        Parent root = loader.load();

        SpielMenueController c = loader.getController();
        c.setName(name);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spiel Menü");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void datenÄndern(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/dateiAendern.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Daten Bearbeiten");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToSpiel(Event event) throws IOException { // Spiel Option
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/spielOption.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spiel Option");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToBestenliste(Event event) throws IOException {
        Parent root = FXMLLoader.load(VueManager.class.getResource("Vue/bestenListe.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - BestenListe");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToSpielNiveau(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/spielNiveau.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spiel Niveau");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToSpielraum(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/spielraum.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spielraum");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToStartFenster(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/startfenster.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Start Fenster");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToLobby(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../Vue/lobby.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Lobby");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


   /*public static int karteNehmen(MouseEvent mouseEvent) {
        return 0;
    }*/
}
