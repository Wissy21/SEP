package gui.controller;

import exceptions.RaumNotExistException;
import exceptions.SpielraumVollException;
import gui.GuiHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import server.datenbankmanager.DBinterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class LobbyRoom {

    @FXML
    Text rname;
    String name;
    public void setName(String username, String raumname) {
        rname.setText(raumname);
        System.out.println(rname.getText());
        name = username;
    }


    public void raum1Beitreten(ActionEvent actionEvent) {
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
            db.raumBeitreten(name,rname.getText());
            //TODO in Raum-GUI gehen
        } catch (NotBoundException | MalformedURLException | RemoteException | SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } catch (SpielraumVollException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Raum voll","Raum ist voll","Dieser Spielraum ist voll.");
        } catch (RaumNotExistException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Raum gibt es nicht mehr","Raum gibt es nicht mehr","Diesen Raum gibt es nicht mehr.");
        }

    }
}
