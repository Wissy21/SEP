package gui.controller;

import gui.GuiHelper;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class VueManager {
    /**
     * Der Login Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToLogIn(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("anmelden.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Registrieren Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToRegister(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("registrieren.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Registrieren");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Menü Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @param name Name des Spielers
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToMenue(Event event, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("spielmenue.fxml"));
        Parent root = loader.load();

        SpielMenueController c = loader.getController();
        c.setName(name);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spiel Menü");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Daten ändern Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @param name Name des Spielers
     * @throws IOException Fehler bei Anzeige
     */
    public static void datenAendern(Event event, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("dateiAendern.fxml"));
        Parent root = loader.load();

        DateiAendernController da = loader.getController();
        da.set(name);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Daten Bearbeiten");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Bestenliste Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @param n Name des Spielers
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToBestenliste(Event event, String n) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("bestenListe.fxml"));
        Parent root = loader.load();

        BestenListeController so = loader.getController();
        so.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - BestenListe");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Spielraum Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @param n Name des Spielers
     * @param raumname Name des Spielraums
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToSpielraum(Event event, String n, String raumname) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("spielraum.fxml"));
        Parent root = loader.load();

        SpielraumController src = loader.getController();
        src.setName(n,raumname);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Spielraum");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Start Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToStartFenster(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("startfenster.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Start Fenster");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Der Lobby Bildschirm wird angezeigt
     * @param event Event das die Methode auslöst
     * @param n Name des Spielers
     * @throws IOException Fehler bei Anzeige
     */
    public static void goToLobby(Event event, String n) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("lobby.fxml"));
        Parent root = loader.load();

        LobbyController lc = loader.getController();
        lc.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Lobby");
        stage.setScene(scene);
        stage.show();
    }

    public static void goToLobby(AnchorPane ap, String n) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueManager.class.getResource("lobby.fxml"));
        Parent root = loader.load();

        LobbyController lc = loader.getController();
        lc.setName(n);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setTitle("Exploding Kittens - Lobby");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Verhalten der GUI beim klicken des Schliessen Knopfes wird hier festgelegt
     * Nur wenn man nicht eingeloggt ist darf man das Fenster schließen, ansonsten wird man aufgefordert sich vorher abzumelden
     * Im Spielraum muss man zusätzlich noch das laufenden Spiel verlassen
     *
     * @param windowEvent Event das die Methode startet
     */
   public static void close(WindowEvent windowEvent) {
        Stage stage = (Stage) windowEvent.getTarget();
        Scene scene = stage.getScene();
        Parent root = scene.getRoot();
        switch(root.getId()) {
            case "startfenster":
            case "registrieren":
            case "anmelden":
                Platform.exit();
                System.exit(0);
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
