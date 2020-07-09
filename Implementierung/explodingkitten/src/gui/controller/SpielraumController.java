package gui.controller;

import exceptions.NichtGenugSpielerException;
import exceptions.NoExplodingKittenException;
import exceptions.NotYourRundeException;
import gui.GuiHelper;
import gui.controller.Spiel.KarteController;
import gui.controller.Spiel.OpponentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.SpielRaum;
import server.SpielRaumInterface;
import server.Spieler;
import server.karten.Karte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SpielraumController {
    @FXML
    public ImageView send;
    @FXML
    public HBox opponents;
    @FXML
    public AnchorPane feld;

    public String name;
    SpielRaumInterface sb;

    public void setName(String name, String raumname){
        this.name = name;
        try {
            sb = (SpielRaumInterface) Naming.lookup("rmi://localhost:1900/spielraum_"+raumname);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

    }

    public void set(){
        try {
            sb.spielStarten();
            int i = 1;
                for (Spieler s :sb.getSpieler()) {
                    if(!s.getNickname().equals(name)) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Opponent.fxml"));
                        VBox root = loader.load();
                        OpponentController oc = loader.getController();
                        oc.set(i,s.getNickname(),8);
                        Platform.runLater(()->this.opponents.getChildren().add(root));
                        i++;
                    } else {
                    for(Karte k : s.handkarte) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../vue/Spiel/Karte.fxml"));
                        ImageView neuekarte = loader.load();
                        KarteController kc = loader.getController();
                        kc.setKarte(k);
                        Platform.runLater(()->this.opponents.getChildren().add(neuekarte));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NichtGenugSpielerException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht genug Spieler","Es sind noch nicht genug Spieler in dem Spielraum.","Warten Sie auf mehr Spieler oder fügen Sie einen Bot hinzu.");
        }


    }

    public void legen(Karte k) {
        try {
            sb.karteLegen(name,k);
        } catch (NotYourRundeException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht am Zug","Nicht dein Zug","");
        } catch (NoExplodingKittenException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nichts zu Entschärfen","Nichts zu entschärfen","");
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

    public void sendmessage(MouseEvent mouseEvent) {
        //TODO
    }

    public void leereStappel(MouseEvent mouseEvent) {
    }

    public void zurückSpielraum(ActionEvent actionEvent) throws IOException {
        //TODO abfangen während spiel
        sb.spielraumVerlassen(name);
        VueManager.goToLobby(actionEvent, name);
    }

    public void ziehen(MouseEvent mouseEvent) {
        //TODO karte ziehen
    }

    public void bot(MouseEvent mouseEvent) {
        //TODO bot hinzufügen
    }

}
