package gui.controller;

import gui.GuiHelper;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.SpielRaum;

import java.io.IOException;

public class VueManager {
    public static void goToLogIn(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/anmelden.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToRegister(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/registrieren.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Registrieren");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToMenue(Event event, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/spielmenue.fxml"));
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

    public static void datenAendern(Event event, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/dateiAendern.fxml"));
        Parent root = loader.load();

        DateiAendernController da = loader.getController();
        da.set(name);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Daten Bearbeiten");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToSpiel(Event event, String n) throws IOException { // Spiel Option
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/spielOption.fxml"));
        Parent root = loader.load();

        SpielOptionController so = loader.getController();
        so.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spiel Option");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToBestenliste(Event event, String n) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/bestenListe.fxml"));
        Parent root = loader.load();

        BestenListeController so = loader.getController();
        so.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - BestenListe");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToSpielraum(Event event, String n, String raumname) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/spielraum.fxml"));
        Parent root = loader.load();

        SpielraumController src = loader.getController();
        src.setName(n,raumname);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spielraum");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToStartFenster(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/startfenster.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Start Fenster");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToLobby(Event event, String n) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("../vue/lobby.fxml"));
        Parent root = loader.load();

        LobbyController lc = loader.getController();
        lc.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Lobby");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }



   public static void close(WindowEvent windowEvent) {
        Stage stage = (Stage) windowEvent.getTarget();
        Scene scene = stage.getScene();
        Parent root = scene.getRoot();
        switch(root.getId()) {
            case "startfenster":
            case "registrieren":
            case "anmelden":
                Platform.exit();
                break;
            case "spielraum":
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Verlassen","Bitte verlassen Sie den Raum durch den vorgesehenen Knopf.","Bitte verlassen Sie den Raum durch den vorgesehenen Knopf.");
                windowEvent.consume();
                break;
            default:
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Verlassen","Bitte melden Sie sich vorher ab.","Bitte melden Sie sich vorher ab.");
                windowEvent.consume();
                break;

        }
   }
}
