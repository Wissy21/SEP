package gui.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import server.LobbyInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class SpielraumController {
    @FXML
    public ImageView send;

    public String name;
    @FXML
    public TextField messageField;
    @FXML
    public ImageView userImage;
    @FXML
    public ImageView myCard;
    @FXML
    public VBox spielraumUser;
    @FXML
    public Label player1;

    public void setName(String n) {
        this.name = n;

        LobbyController lbC = new LobbyController();
        lbC.updateMessageList();

        Random r = new Random();
        int index = r.nextInt(9-2) + 2;
        String imgPth = "../images/user_images/playericon" + index + ".png";
        userImage.setImage(new Image(this.getClass().getResource(imgPth).toString()));
    }


    public void onInput(ActionEvent actionEvent) {
    }


    public void onInputText(InputMethodEvent inputMethodEvent) {
    }

    public void sendmessage(Event mouseEvent) {
        try {
            LobbyInterface lb = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");

            Timestamp tm = new Timestamp(new Date().getTime());
            lb.sendMessage(messageField.getText(), tm.toString(), name);
            messageField.clear();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void leereStappel(MouseEvent mouseEvent) {
    }

    public void zurückSpielraum(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpiel(actionEvent, name);
    }

    public void stappel(MouseEvent mouseEvent) {
    }

    public void karte(MouseEvent mouseEvent) {
    }

    public void play(ActionEvent actionEvent) {
    }
}
