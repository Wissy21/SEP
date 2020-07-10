package gui.controller.Spiel;

import exceptions.NoExplodingKittenException;
import exceptions.NotYourRundeException;
import gui.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import server.SpielRaumInterface;
import server.karten.Karte;

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




    public void setKarte(Karte k,String name,String raumname) {
        this.k = k;
        this.name = name;
        this.raumname = raumname;
        karte.setImage(new Image(getClass().getResource("../../images/Karten/"+k.getEffekt()+".png").toString(),0,150,true,false));
        try {
            sb = (SpielRaumInterface) Naming.lookup("rmi://localhost:1900/spielraum_"+raumname);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
    /*
    public void bewegen(MouseEvent mouseEvent) {

        ImageView p = (ImageView) mouseEvent.getTarget();

        p.setX(mouseEvent.getX()-53);
        p.setY(mouseEvent.getY()-75);

    }*/

    public void legen(MouseEvent mouseEvent) {

        try {
            sb.karteLegen(name,k);
        } catch (NotYourRundeException e) {
            try {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nicht am Zug","Nicht dein Zug","Es ist der Zug von "+sb.getCurrent().getNickname());
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        } catch (NoExplodingKittenException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Nichts zu Entschärfen","Nichts zu entschärfen","Glück gehabt!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
/*
    public void record(MouseEvent mouseEvent) {
        ImageView r = (ImageView) mouseEvent.getTarget();
        System.out.println(k.getName()+"\t"+k.getEffekt());

        ogX = r.getX();
        ogY = r.getY();

    }*/
}
