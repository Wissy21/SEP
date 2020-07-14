package main.client;

import main.client.gui.controller.VueManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SpielenClient extends Application {

    /**
     * Erstellt die GUI
     *
     * @param primaryStage Stage auf der die unterschiedlichen Bildschirme angezeigt werden
     * @throws Exception Mogliche Fehler
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(SpielenClient.class.getResource("gui/vue/serverVerbindung.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Exploding Kittens - Anmelden");
        primaryStage.getIcons().add(new Image(SpielenClient.class.getResourceAsStream("gui/images/explode.png")));

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                VueManager.close(windowEvent);
            }
        });
        primaryStage.show();
        primaryStage.centerOnScreen();
    }


    /**
     * Startet den Client
     *
     * @param args Eingaben
     */
    public static void main(String[] args) {
        launch(args);
    }

}
