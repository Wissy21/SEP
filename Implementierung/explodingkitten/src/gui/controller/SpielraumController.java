package gui.controller;

import exceptions.NichtGenugSpielerException;
import exceptions.NotYourRundeException;
import exceptions.SpielraumVollException;
import gui.GuiHelper;
import gui.controller.Spiel.KarteController;
import gui.controller.Spiel.OpponentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server.Nachricht;
import server.SpielRaumInterface;
import server.Spieler;
import server.karten.Karte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class SpielraumController implements IRaumObserver{
    @FXML
    public ImageView send;
    @FXML
    public HBox opponents;
    @FXML
    public AnchorPane feld;
    @FXML
    public HBox cardbox;
    @FXML
    public ScrollPane spane;
    @FXML
    TextField messageField;
    @FXML
    public VBox messageList;

    public String name;
    public String raumname;
    public int lastMessage = 0;
    SpielRaumInterface sb;

    public void setName(String name, String raumname){
        this.name = name;
        this.raumname = raumname;
        cardbox.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        try {
            RaumObserver ro = new RaumObserver(this);
            sb = (SpielRaumInterface) Naming.lookup("rmi://localhost:1900/spielraum_"+raumname);
            sb.registerObserver(name,ro);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
            updateMessageList();
    }

    public void set(){
        try {
            if(!sb.isRunning()) {
                try {
                    sb.spielStarten();
                    Thread.sleep(2000);
                    int i = 1;
                    for (Spieler s : sb.getSpieler()) {
                        if (!s.getNickname().equals(name)) {
                            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Opponent.fxml"));
                            Parent root = loader.load();
                            OpponentController oc = loader.getController();
                            oc.set(i, s.getNickname(), 8);
                            Platform.runLater(() -> this.opponents.getChildren().add(root));
                            i++;
                        } else {
                            for (Karte k : s.getHandkarte()) {
                                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Karte.fxml"));
                                Parent neuekarte = loader.load();
                                KarteController kc = loader.getController();
                                kc.setKarte(k, name, raumname);
                                Platform.runLater(() -> this.cardbox.getChildren().add(neuekarte));
                            }
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (NichtGenugSpielerException e) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht genug Spieler", "Es sind noch nicht genug Spieler in dem Spielraum.", "Warten Sie auf mehr Spieler oder fügen Sie einen Bot hinzu.");
                }
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spiel läuft bereits","Spiel läuft bereits","");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


    public void zeigeRegeln() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Regeln.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Regeln");
        stage.setScene(new Scene(root,2000,755));
        stage.show();
    }



    public void onInput(ActionEvent actionEvent) {
        //TODO
    }


    public void onInputText(InputMethodEvent inputMethodEvent) {
        //TODO
    }

    public void sendmessage(Event mouseEvent) {
        try {
            Timestamp tm = new Timestamp(new Date().getTime());
            sb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void leereStappel(Event mouseEvent) {
    }


    public void zurueckSpielraum(ActionEvent actionEvent) throws IOException {
        if(sb.isRunning()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Spiel Verlassen?");
            alert.setHeaderText("Wollen Sie das Spiel wirklich verlassen?");
            alert.setContentText("Sie haben dann keine Chance mehr zu gewinnen.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //TODO aufgeben, Karten ablegen
                sb.spielraumVerlassen(name);
                VueManager.goToLobby(actionEvent, name);

            }
        } else {
            sb.spielraumVerlassen(name);
            VueManager.goToLobby(actionEvent, name);
        }
    }

    public void ziehen(MouseEvent mouseEvent) {
        try {
            sb.zugBeenden(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotYourRundeException e) {
            try {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht am Zug","Nicht dein Zug","Es ist der Zug von "+sb.getCurrent().getNickname());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
    }

    public void bot(ActionEvent mouseEvent) {
        try {
            if(!sb.isRunning()) {
                try {
                    sb.botHinzufuegen();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SpielraumVollException e) {
                    GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spielraum ist voll","Spielraum ist voll","Kein Platz mehr.");
                }
            } else {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spiel läuft bereits","Spiel läuft bereits","");

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMessageList(){
        try {
            ArrayList<Nachricht> nachrichten = sb.getMessage();

            for(int i = lastMessage; i<nachrichten.size(); i++){
                Nachricht n = nachrichten.get(i);
                FXMLLoader fxmlLoader;
                Parent root;
                if(n.sender.equals(name)) {
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/rightMessage.fxml"));
                    root = fxmlLoader.load();
                    RightMessageController rc = fxmlLoader.getController();

                    rc.set(n.message, n.sender, n.time);
                } else {
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../vue/leftMessage.fxml"));
                    root = fxmlLoader.load();
                    LeftMessageController lc = fxmlLoader.getController();

                    lc.set(n.message, n.sender, n.time);
                }

                Platform.runLater(() -> this.messageList.getChildren().add(root));
                lastMessage++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
