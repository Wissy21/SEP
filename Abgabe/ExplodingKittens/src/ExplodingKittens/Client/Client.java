package ExplodingKittens.Client;

import ExplodingKittens.Exceptions.UserNotExistException;
import ExplodingKittens.Exceptions.UsernameTakenException;
import ExplodingKittens.Exceptions.WrongPasswordException;
import ExplodingKittens.Lobby.LobbyClient;
import ExplodingKittens.Login.LoginClient;
import ExplodingKittens.Server.Server;
import ExplodingKittens.User.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse die den Client implementiert
 */
public class Client extends Application implements LoginClient, LobbyClient {
    Server server;
    User user;

    /**
     * Konstruktor der Client und Server miteinander verbindet
     *
     * @throws RemoteException      Fehler bei datenübertragung
     * @throws NotBoundException    Fehler wenn Server nicht vorhanden
     */
    public Client() throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry();
        server = (Server) reg.lookup("login-server");
    }

    /**
     * Methode die den Client startet
     *
     * @param args Ungenutzte Eingabeparameter
     */
    public static void main(String[] args) {
            launch(args);
    }

    /**
     * Methode die das grafische Interface startet
     *
     * @param stage Hier werden die verscheidenen Grafiken angezeigt
     */
    @Override
    public void start(Stage stage) {
        selection(stage);
    }

    /**
     * Siehe LoginClient
     */
    @Override
    public void selection(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,500,300);
        stage.setTitle("Anmelden");
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
                loginGUI(stage);
            }
        });
        Button reg = new Button("Registrieren");
        /** Beim drücken des "Registrieren" Knopfes gelangt man ins Regestrierungsfenster */
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                registerGUI(stage);
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
        /** Anzeigen der erstellten Grafik */
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Siehe LoginClient
     *
     * Die Benutzerdaten können eingegeben werden und bei richtigen Daten gelangt man in die Lobby
     */
    @Override
    public void loginGUI(Stage stage) {

        Scene scene = stage.getScene();
        Parent previous = scene.getRoot();
        BorderPane loginPane = new BorderPane();
        Label l = new Label("Bitte geben Sie ihren Namen und das Passwort ein.");
        l.setFont(new Font(15));
        l.setPadding(new Insets(50,0,50,0));
        loginPane.setTop(l);
        loginPane.setAlignment(l,Pos.CENTER);
        /** Erstellen der Eingabeflächen */
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
        /** Erstellen der Knöpfe */
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
                    user = new User(username.getCharacters().toString());
                    displayLobby(stage);
                } catch (WrongPasswordException e) {
                    popup("Das eingegebene Passwort ist falsch!");
                } catch (UserNotExistException e) {
                    popup("Die eingegebenen Nutzerdaten sind nicht korrekt!");
                } catch (RemoteException e) {
                    e.printStackTrace();
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
        /** Anzeigen der Grafik */
        scene.setRoot(loginPane);
    }

    /**
     * siehe LoginClient
     *
     * Die Benutzerdaten können eingegeben werden und bei richtigen Daten gelangt man in die Lobby
     */
    @Override
    public void registerGUI(Stage stage) {

        Scene scene = stage.getScene();
        Parent previous = scene.getRoot();
        BorderPane registerPane = new BorderPane();
        Label l = new Label("Bitte geben Sie ihren gewünschten Namen und das Passwort ein.");
        l.setFont(new Font(15));
        l.setPadding(new Insets(50,0,50,0));
        registerPane.setTop(l);
        registerPane.setAlignment(l,Pos.CENTER);
        /** Erstellen der Eingabeflächen */
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
        /** Erstellen der Knöpfe */
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
                    } else {
                        server.register(username.getCharacters().toString(),password.getCharacters().toString());
                        user = new User(username.getCharacters().toString());
                        displayLobby(stage);
                    }
                } catch (UsernameTakenException e) {
                    popup("Die eingegebenen Benutzername ist bereits vergeben!");
                } catch (RemoteException e) {
                    e.printStackTrace();
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
    /** Anzeigen der Grafik */
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

    /**
     * siehe LobbyClient
     *
     * Es gibt die Möglichkeit: sich abzumelden,
     *                          die Bestenliste anzusehen,
     * TODO:                    zu chatten und
     *                          einen Spielraum zu erstellen/beizutreten
     */
    @Override
    public void displayLobby(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);
        /** Erstellen der Knöpfe */
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(0,20,20,150));
        buttons.setSpacing(50);

        Button logoff = new Button("Abmelden");
        logoff.setOnAction(new EventHandler<ActionEvent>() {
            /** Beim drücken des "Abmelden" Knopfes wird man aus der Lobby zum Anmeldebildschirm gebracht */
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    server.removeUser(user);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                user = null;
                selection(stage);
            }
        });

        Button bliste = new Button("Bestenliste");
        /** Beim drücken des "Bestenliste" Knopfes wird die Methode showBestenlsite ausgeführt */
        bliste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    showBestenliste();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        logoff.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.getChildren().addAll(logoff, bliste);
        root.setBottom(buttons);
        /** Nachdem alles erstellt ist wird die Lobby angezeigt */
        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * siehe LobbyClient
     *
     * Die Bestenliste wird als Tabelle angezeigt.
     * Dafür wird ein neues Fenster geöffnet.
     */
    @Override
    public void showBestenliste() throws RemoteException {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        HashMap<String,Integer> liste = server.getBestenliste();
        /** Erstellen der Tabelle */
        Label sp = new Label("Spieler");
        grid.add(sp, 0, 0);
        GridPane.setHalignment(sp,HPos.CENTER);
        GridPane.setHgrow(sp,Priority.ALWAYS);
        Label si = new Label("Siege");
        grid.add(si, 1, 0);
        GridPane.setHalignment(si,HPos.CENTER);
        GridPane.setHgrow(si,Priority.ALWAYS);
        grid.setGridLinesVisible(true);
        int i = 1;
        /**
         * Auslesen von jedem Eintrag in der Bestenliste und einfügen in die Tabelle
         */
        for (Map.Entry<String,Integer> e : liste.entrySet()) {
            Label n = new Label(e.getKey());
            Label a =new Label(e.getValue().toString());
            grid.add(n, 0, i);
            GridPane.setHgrow(n,Priority.ALWAYS);
            GridPane.setVgrow(n,Priority.ALWAYS);
            GridPane.setHalignment(n,HPos.CENTER);

            grid.add(a, 1, i);
            GridPane.setHgrow(a,Priority.ALWAYS);
            GridPane.setVgrow(a,Priority.ALWAYS);
            GridPane.setHalignment(a,HPos.CENTER);


            i++;
        }
        grid.setAlignment(Pos.TOP_CENTER);
        root.setCenter(grid);
        /**
         * Neues Fenster erstellen in dem die Bestenliste angezeigt wird
         */
        Stage bStage = new Stage();
        bStage.setTitle("Bestenliste");
        Scene bScene = new Scene(root,500,500);
        bStage.setScene(bScene);
        bStage.show();
    }

    /**
     * TODO
     * @param stage
     */
    @Override
    public void leaveLobbyToRoom(Stage stage) {

    }

}
