package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Lobby extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("lobby.fxml"));
        primaryStage.setTitle("Lobby");
        ScrollPane sp = new ScrollPane();
        primaryStage.setScene(new Scene(root, 600, 380));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

