package ExplodingKittens.Client;

import ExplodingKittens.Chat.ChatClient;
import ExplodingKittens.Exceptions.*;
import ExplodingKittens.Lobby.LobbyClient;
import ExplodingKittens.Login.LoginClient;
import ExplodingKittens.Server.Server;
import ExplodingKittens.Spielraum.Spielraum;
import ExplodingKittens.Spielraum.SpielraumClient;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Klasse die den Client implementiert
 */
public class Client extends Application implements LoginClient, LobbyClient, SpielraumClient, ChatClient {
    Server server;
    User user = null;
    Spielraum room = null;

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
        /* Falls man angemeldet ist und das Fenster schliessen will wird man aufgefordert sich zuerst abzumelden. */
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if(user != null) {
                    popup("Bitte melden Sie sich ab.");
                    windowEvent.consume();
                }
            }
        });
    }

    /**
     * Siehe LoginClient
     */
    @Override
    public void selection(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,500,300);
        stage.setTitle("Exploding Kittens - Anmelden");
        /* Knöpfe die zum anmelden oder registrieren weiterleiten */
        HBox box = new HBox();
        box.setPadding(new Insets(50,0,0,0));
        box.setSpacing(50);
        Button log = new Button();
        log.setText("Anmelden");
        /* Beim drücken des "Anmelden" Knopfes gelangt man ins Anmeldefenster */
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loginGUI(stage);
            }
        });
        Button reg = new Button("Registrieren");
        /* Beim drücken des "Registrieren" Knopfes gelangt man ins Regestrierungsfenster */
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                registerGUI(stage);
            }
        });
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(log,reg);
        root.setCenter(box);

        Label l = new Label("Wollen Sie sich anmelden oder registrieren?");
        l.setFont(new Font(15));
        l.setPadding(new Insets(50,0,50,0));
        root.setTop(l);
        BorderPane.setAlignment(l,Pos.CENTER);

        /* Anzeigen der erstellten Grafik */
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
        BorderPane.setAlignment(l,Pos.CENTER);
        /* Erstellen der Eingabeflächen */
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
        grid.setAlignment(Pos.CENTER_LEFT);
        loginPane.setCenter(grid);
        /* Erstellen der Knöpfe */
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10,0,20,0));
        buttons.setSpacing(50);
        Button login = new Button("Anmelden");
        /*
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
        /* Beim drücken des "Zurück" Knopfes gelangt man wieder zum Auswahlfenster */
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scene.setRoot(previous);
            }
        });
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(login,back);
        loginPane.setBottom(buttons);
        /* Anzeigen der Grafik */
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
        BorderPane.setAlignment(l,Pos.CENTER);
        /* Erstellen der Eingabeflächen */
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
        grid.setAlignment(Pos.CENTER_LEFT);
        registerPane.setCenter(grid);
        /* Erstellen der Knöpfe */
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10,0,20,0));
        buttons.setSpacing(50);
        Button register = new Button("Registrieren");
        /*
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
        /* Beim drücken des "Zurück" Knopfes gelangt man wieder zum Auswahlfenster */
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scene.setRoot(previous);
            }
        });
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(register,back);
        registerPane.setBottom(buttons);
    /* Anzeigen der Grafik */
        scene.setRoot(registerPane);
    }

    /**
     * siehe LobbyClient
     *
     * Es gibt die Möglichkeit: sich abzumelden,
     *                          die Bestenliste anzusehen,
     *                          zu chatten und
     *                          einen Spielraum zu erstellen/beizutreten
     */
    @Override
    public void displayLobby(Stage stage) throws RemoteException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);

        /* Einfügen des Chats */
        VBox combined = new VBox();
        ScrollPane chat = new ScrollPane();
        VBox messages = new VBox(5);
        Label placehold = new Label("Willkommen im Chat!");
        messages.getChildren().add(placehold);
        chat.setContent(messages);
        HBox messageboard = new HBox();
        TextField nachricht = new TextField();
        nachricht.setPromptText("Nachricht");
        Button senden = new Button("Senden");
        /* Beim drücken des "Senden" Knopfes wird die eingetippte Nachricht gesendet, wenn einen Text enthält */
        senden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String message = nachricht.getCharacters().toString();
                nachricht.clear();
                if(message.length()>1) {
                    messages.getChildren().add(displayMessage(user,message));
                }
            }
        });
        senden.setMaxWidth(100);
        HBox.setHgrow(nachricht,Priority.ALWAYS);
        messageboard.getChildren().addAll(nachricht,senden);
        combined.setAlignment(Pos.BOTTOM_RIGHT);
        combined.getChildren().addAll(chat,messageboard);

        /* Erstellen der Spielraum-Anzeige */
        ScrollPane roomPane = new ScrollPane();
        VBox rboxes = new VBox(30);

        for (Map.Entry<String,Spielraum> e: server.getRooms().entrySet()) {
            System.out.println(e.getKey());
            addCreatedRoom(e.getValue(), rboxes,stage);
        }
        rboxes.setPadding(new Insets(0,0,0,40));
        roomPane.setContent(rboxes);

        /* Zusammenfügen von Chat und Raumauswahl zur Mittelkonsole */
        HBox console = new HBox();
        HBox.setHgrow(roomPane,Priority.ALWAYS);
        HBox.setHgrow(combined,Priority.ALWAYS);
        console.getChildren().addAll(roomPane, combined);
        root.setCenter(console);

        /* Erstellen der Knöpfe */
        HBox buttons = new HBox(100);
        buttons.setPadding(new Insets(50,20,20,20));
        buttons.setAlignment(Pos.CENTER);

        Button logoff = new Button("Abmelden");
        logoff.setOnAction(new EventHandler<ActionEvent>() {
            /* Beim drücken des "Abmelden" Knopfes wird man aus der Lobby zum Anmeldebildschirm gebracht */
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
        /* Beim drücken des "Bestenliste" Knopfes wird die Bestenliste in einem neuen Fenster angezeigt */
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

        Button newroom = new Button("Raum erstellen");
        /*
        Beim drücken des "Raum erstellen" Knopfes wird ein neuer Raum mit eingegebenen Namen erstellt
        Aber nur wenn der Name nicht schon vergeben ist
         */
        newroom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String rname = popupText();
                try {
                    room = server.createRoom(user, rname);
                    addCreatedRoom(room,rboxes,stage);
                    displaySpielraum(stage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (RoomIsFullException e) {
                    popup("Der Raum ist bereits voll.");
                } catch (RoomNameTakenException e) {
                    popup("Der Raumname ist bereits vergeben.");
                } catch (NoInputException e) {
                    popup("Bitte geben Sie einen gültigen Namen ein. (Mindestens 4 Zeichen.)");
                }
            }
        });
        /*
        Einfügen der Knöpfe in die Lobby Grafik
         */
        buttons.getChildren().addAll(newroom,bliste,logoff);
        root.setBottom(buttons);



        /* Erstellen der Navigation */
        HBox identifier = new HBox();
        identifier.setPadding(new Insets(30,30,50,30));
        Label nav1 = new Label("Spielräume");
        Label nav2 = new Label("Chat");
        Label navname = new Label("Eingeloggt als: "+user.getName());

        Region buffer1 = new Region();
        Region buffer2 = new Region();
        buffer1.setPrefWidth(0);
        buffer2.setPrefWidth(0);
        HBox.setHgrow(buffer1,Priority.ALWAYS);
        HBox.setHgrow(buffer2,Priority.ALWAYS);

        identifier.getChildren().addAll(nav1,buffer1,navname,buffer2,nav2);
        root.setTop(identifier);


        /* Nachdem alles erstellt ist wird die Lobby angezeigt */
        stage.setTitle("Exploding Kittens - Lobby");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * siehe LobbyClient
     *
     * Die Bestenliste wird als Tabelle angezeigt.
     * Dafür wird ein neues Fenster geöffnet.
     *
     * TODO:    Bestenliste nach Anzahl der Siege ordnen
     */
    @Override
    public void showBestenliste() throws RemoteException {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        HashMap<String,Integer> liste = server.getBestenliste();
        /* Erstellen der Tabelle */
        grid.setPadding(new Insets(10,100,20,100));
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
        /* Auslesen von jedem Eintrag in der Bestenliste und einfügen in die Tabelle */
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
        /* Neues Fenster erstellen in dem die Bestenliste angezeigt wird */
        Stage bStage = new Stage();
        bStage.setTitle("Exploding Kittens - Bestenliste");
        Scene bScene = new Scene(root,500,500);
        bStage.setScene(bScene);
        bStage.show();
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
     * Methode mit deren Hilfe der Name eines Spielraumes eigegeben wird
     *
     * @return  Der eingegebene Name wird zurück gegeben, wenn er länger als 3 Zeichen ist
     */
    public String popupText() {
        TextInputDialog dialog = new TextInputDialog("Raumname");
        dialog.setTitle("Raumbenennung");
        dialog.setHeaderText(null);
        dialog.setContentText("Benennen Sie ihren Raum:");
        /* Prüft ob Eingabe korrekt ist*/
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().length()>3){
                return result.get();
        }
            return null;
    }

    /**
     * Methode um einen Raum in die Anzeige der Lobby aufzunehmen
     *
     * @param room      Raum der angezeigt werden soll
     * @param rboxes    Box in der sich alle Räume befinden, die angezeigt werden
     * @param stage     Stage wird mit übergeben um nächsten Räume anzuzeigen
     */
    public void addCreatedRoom(Spielraum room, VBox rboxes,Stage stage) {
        VBox rbox = new VBox(new Label(room.getName()));
        rbox.setPadding(new Insets(20));
        rbox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,null)));
        /*
        Wenn man in der Lobby auf einen Raum klickt wird man in diesen verschoben und wechselt die Ansicht
        Aber nur wenn in dem Raum noch Platz ist
         */
        rbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    server.enterRoom(user,room);
                    displaySpielraum(stage);
                } catch (RemoteException e) {
                    popup("Dieser Raum existiert nicht mehr");
                } catch (RoomIsFullException e) {
                    popup("Dieser Raum ist leider voll.");
               }
            }
        });
        rboxes.getChildren().add(rbox);
    }

    /**
     * Methode um den Spielraum anzuzeigen
     *
     * @param stage             Stage auf der der Spielraum zu sehen ist
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    @Override
    public void displaySpielraum(Stage stage) throws RemoteException {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1000,1000);



        /* Erstellen der Knöpfe */
        HBox buttons = new HBox(100);
        buttons.setPadding(new Insets(50,20,20,20));
        buttons.setAlignment(Pos.CENTER);

        Button leave = new Button("Verlassen");
        /*
        Beim drücken des "Verlassen" Knopfes wird man aus dem Spielraum in die Lobby gebracht
        Falls der Raum danach leer ist oder nur noch Bots enthält wird er geschlossen
         */
        leave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp = room.getName();
                boolean empty = room.leaveRoom(user);
                if(empty) {
                    try {
                        server.updateRoom(temp,room);
                        server.deleteRoom(room);
                        displayLobby(stage);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    room = null;
                }

            }
        });

        Button startgame = new Button("Spiel starten");
        /* Beim drücken des "Spiel starten" Knopfes werden die Spieler in ein Spiel gebracht, wenn es genug Spieler im Raum gibt */
        startgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO
                popup("TODO");
            }
        });

        Button botadd = new Button("Bot hinzufügen");
        /* Beim drücken des "Bot hinzufügen" Knopfes wird ein Bot in den Raum hinzugefügt, wenn genugt Platz ist und man der Besitzer des Raums ist */
        botadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String temp = room.getName();
                    room.addBot(user);
                    server.updateRoom(temp,room);
                } catch (RoomIsFullException e) {
                    popup("Der Raum ist bereits voll.");
                } catch (NoPermissionException e) {
                    popup("Dazu sind Sie nicht berechtigt.");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button namechange = new Button("Name ändern");
        /* Beim drücken des "Name ändern" Knopfes wird der Name des Raums geändert, wenn man der Besitzer des Raums ist */
        namechange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String newname = popupText();
                try {
                    String temp = room.getName();
                    System.out.println(room.getName());
                    room.changeName(user,newname);
                    System.out.println(room.getName());
                    server.updateRoom(temp,room);
                } catch (NoPermissionException e) {
                    popup("Dazu sind Sie nicht berechtigt.");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        /* Hinzufügen der Knöpfe in die Ansicht */
        buttons.getChildren().addAll(startgame,botadd,namechange,leave);
        root.setBottom(buttons);
        /* Anzeigen der Ansicht */
        stage.setTitle("Exploding Kittens - Spielraum");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Methode die eine geschriebene Nachricht im Chat sichtbar macht
     *
     * @param user                  Benutzer der die Nachricht geschrieben hat
     * @param message               Nachricht die im Chat angezeigt werden soll
     * @return                      Label in dem die Nachricht steht
     * @throws RemoteException
     */
    @Override
    public Label displayMessage(User user, String message) {
        return new Label(user.getName()+":\t" + message);
    }
}
