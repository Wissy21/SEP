package ExplodingKittens.Login;

import ExplodingKittens.Exceptions.UserNotExistException;
import ExplodingKittens.Exceptions.UsernameTakenException;
import ExplodingKittens.Exceptions.WrongPasswordException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Klasse die den Client zur Anmeldung/Registrierung implementiert
 */
public class Client extends Application {
    LoginServer server;

    /**
     * Konstruktor der Client und Server miteinander verbindet
     *
     * @throws RemoteException      Fehler bei datenübertragung
     * @throws NotBoundException    Fehler wenn Server nicht vorhanden
     */
    public Client() throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry();
        server = (LoginServer) reg.lookup("login-server");
    }

    /**
     * Methode die den Client startet
     *
     * @param args
     */
    public static void main(String[] args) {
            launch(args);
    }

    /**
     * Methode die das grafische Interface startet
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        BorderPane root = selection();
        Scene scene = new Scene(root,500,300);
        stage.setTitle("Anmelden");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Erster Audswahlbildschirm bei dem man sich entscheiden kann,
     * ob man sich registrieren oder anmelden will
     *
     * @return  Die fertiggestellte Ansicht wird dem grafischen Interface übergeben, das es dann anzeigt
     */
    public BorderPane selection() {
        BorderPane root = new BorderPane();
        /** Knöpfe die zum anmelden oder registrieren weiterleiten */
        HBox box = new HBox();
        box.setPadding(new Insets(50,0,0,150));
        box.setSpacing(50);
        Button log = new Button();
        log.setText("Anmelden");
        /** Beim drücken des "Anmelden" Knopfes gelangt man ins Anmeldefenster */
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loginGUI(root.getScene());
            }
        });
        Button reg = new Button("Registrieren");
        /** Beim drücken des "Registrieren" Knopfes gelangt man ins Regestrierungsfenster */
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                registerGUI(root.getScene());
            }
        });
        box.getChildren().addAll(log,reg);
        root.setCenter(box);
        root.setAlignment(box,Pos.CENTER);

        Label t = new Label("Wollen Sie sich anmelden oder registrieren?");
        t.setFont(new Font(15));
        t.setPadding(new Insets(50,0,0,0));
        root.setTop(t);
        root.setAlignment(t,Pos.CENTER);

        return root;
    }

    /**
     * Methode zum Anzeigen des Anmeldefensters
     * Die Benutzerdaten können eingegeben werden und bei richtigen Daten gelangt man in die Lobby
     *
     * @param scene Szene wird mit übergeben, um die Ansichen zu ändern.
     */
    public void loginGUI(Scene scene) {

        Parent previous = scene.getRoot();
        BorderPane loginPane = new BorderPane();
        Label l = new Label("Bitte geben Sie ihren Namen und das Passwort ein.");
        l.setFont(new Font(15));
        l.setPadding(new Insets(50,0,50,0));
        loginPane.setTop(l);
        loginPane.setAlignment(l,Pos.CENTER);

        TextField username = new TextField();
        username.setPromptText("Benutzername");
        PasswordField password = new PasswordField();
        password.setPromptText("Passwort");

        GridPane grid = new GridPane();

        grid.add(new Label("Benutzername:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Passwort:"), 0, 1);
        grid.add(password, 1, 1);
        grid.setHgap(50);

        loginPane.setCenter(grid);

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(0,20,20,150));
        buttons.setSpacing(50);
        Button login = new Button("Anmelden");
        /**
         * Beim drücken des "Anmelden" Knopfes werden die Eingaben überprüft.
         * Bei Falschen Eingaben bekommt man eine Fehlermeldung.
         */
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    server.login(username.getCharacters().toString(),password.getCharacters().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (WrongPasswordException e) {
                    popup("Das eingegebene Passwort ist falsch!");
                } catch (UserNotExistException e) {
                    popup("Die eingegebenen Nutzerdaten sind nicht korrekt!");
                }
            }
        });
        Button back = new Button("Zurück");
        /** Beim drücken des "Zurück" Knopfes gelangt man wieder zum Auswahlfenster */
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scene.setRoot(previous);
            }
        });
        buttons.getChildren().addAll(login,back);
        loginPane.setBottom(buttons);

        scene.setRoot(loginPane);

    }

    /**
     * Methode zum Anzeigen des Registrierungsfensters
     * Die Benutzerdaten können eingegeben werden und bei richtigen Daten gelangt man in die Lobby
     *
     * @param scene Szene wird mit übergeben, um die Ansichen zu ändern.
     */
    public void registerGUI(Scene scene) {

        Parent previous = scene.getRoot();
        BorderPane registerPane = new BorderPane();
        Label l = new Label("Bitte geben Sie ihren gewünschten Namen und das Passwort ein.");
        l.setFont(new Font(15));
        l.setPadding(new Insets(50,0,50,0));
        registerPane.setTop(l);
        registerPane.setAlignment(l,Pos.CENTER);

        TextField username = new TextField();
        username.setPromptText("Benutzername");
        PasswordField password = new PasswordField();
        password.setPromptText("Passwort");

        GridPane grid = new GridPane();

        grid.add(new Label("Benutzername:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Passwort:"), 0, 1);
        grid.add(password, 1, 1);
        grid.setHgap(50);

        registerPane.setCenter(grid);

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(0,20,20,150));
        buttons.setSpacing(50);
        Button register = new Button("Registrieren");
        /**
         * Beim drücken des "Registrieren" Knopfes werden die Eingaben überprüft.
         * Bei zu kurzen Eingaben oder bei einem berites vorhandenen Namen bekommt man eine Fehlermeldung.
         */
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if(username.getCharacters().length()<4) {
                        popup("Der Nutzername ist zu kurz!(Mindestens 4 Zeichen)");
                    } else if (password.getCharacters().length()<4) {
                        popup("Das Passwort ist zu kurz!(Mindestens 4 Zeichen)");
                    }
                    server.register(username.getCharacters().toString(),password.getCharacters().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (UsernameTakenException e) {
                    popup("Die eingegebenen Benutzername ist bereits vergeben!");
                }
            }
        });
        Button back = new Button("Zurück");
        /** Beim drücken des "Zurück" Knopfes gelangt man wieder zum Auswahlfenster */
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scene.setRoot(previous);
            }
        });
        buttons.getChildren().addAll(register,back);
        registerPane.setBottom(buttons);

        scene.setRoot(registerPane);
    }

    /**
     * Methode zum Anzeigen von Fehlermeldungen
     *
     * @param message   Nachricht die mit der Fehlermeldung angezeigt werden soll.
     */
    public void popup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
