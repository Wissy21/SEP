package gui;

import gui.controller.VueManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    /**
     * Erstellt die GUI
     *
     * @param primaryStage Stage auf der die unterschiedlichen Bildschirme angezeigt werden
     * @throws Exception Mogliche Fehler
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("serverVerbindung.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Exploding Kittens - Anmelden");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/explode.png")));

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
