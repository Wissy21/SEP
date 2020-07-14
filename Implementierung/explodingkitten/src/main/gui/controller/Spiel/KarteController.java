package main.gui.controller.Spiel;

import main.exceptions.NoExplodingKittenException;
import main.exceptions.NotYourRundeException;
import main.gui.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.server.SpielRaumInterface;
import main.server.karten.Karte;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class KarteController {

    @FXML
    ImageView karte;

    SpielRaumInterface sb;
    String name;
    String raumname;
    Karte k;

    String serverIp;


    /**
     * Initialisiert die Karte für die GUI
     *
     * @param k        Karte die dargestellt werden soll
     * @param name     Name des Spielers, dem die Karte gehört
     * @param raumname Name des Raums, in dem sich die Karte befindet
     */
    public void setKarte(Karte k, String name, String raumname,String ip) {
        this.k = k;
        this.name = name;
        this.raumname = raumname;
        this.serverIp = ip;
        karte.setImage(new Image(getClass().getResource("../../images/Karten/" + k.getEffekt() + ".png").toString(), 0, 150, true, false));
        karte.setId(k.getName());
        try {
            System.out.println("Karten IP: " + serverIp);
            sb = (SpielRaumInterface) Naming.lookup("rmi://" + serverIp + ":1900/spielraum_" + raumname);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Versucht die Karte zu legen und deren Effekt auszuführen
     * Erstellt Warnungs Dialoge für jede Exception die das aufhält
     *
     * @param mouseEvent Event das die Methode auslöst
     */
    public void legen(MouseEvent mouseEvent) {

        try {
            sb.karteLegen(name, k);
        } catch (NotYourRundeException e) {
            try {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nicht am Zug", "Nicht dein Zug", "Es ist der Zug von " + sb.getCurrent().getNickname());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        } catch (NoExplodingKittenException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Nichts zu Entschärfen", "Nichts zu entschärfen", "Glück gehabt!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
