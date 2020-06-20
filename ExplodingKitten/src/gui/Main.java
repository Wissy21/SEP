package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Vue/anmelden.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Exploding Kittens - Anmelden");
        primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("images/explode.png")) );

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
