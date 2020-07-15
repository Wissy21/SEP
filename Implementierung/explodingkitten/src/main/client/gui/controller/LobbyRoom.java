package main.client.gui.controller;

import main.exceptions.RaumNotExistException;
import main.exceptions.SpielLauftBereitsException;
import main.exceptions.SpielraumVollException;
import main.client.gui.GuiHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import main.server.SpielRaumInterface;
import main.server.datenbankmanager.DBinterface;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;

public class LobbyRoom {

    @FXML
    Text rname;
    String name;
    String serverIp;

    /**
     * Initialisiert den Raum in der Raumliste der GUI
     * @param username Name des angemeldeten Nutzers
     * @param raumname Name des Raums, der hierdurch dargestellt wird
     * @param serverIp
     */
    public void setName(String username, String raumname, String serverIp) {
        rname.setText(raumname);
        name = username;
        this.serverIp = serverIp;
    }


    /**
     * Lässt den Nutzer den ausgewählten Raum betreten
     * Bei Fehlern werden Popups zur Erklärung erstellt
     * @param actionEvent Event das die Methode ausgelöst hat
     */
    public void raum1Beitreten(ActionEvent actionEvent) {
        try {
            System.out.println(serverIp);
            DBinterface db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
            SpielRaumInterface si = (SpielRaumInterface) Naming.lookup("rmi://" + serverIp + ":1900/spielraum_"+rname.getText());
            db.raumBeitreten(name,rname.getText());
            si.betreten(name);
            VueManager.goToSpielraum(actionEvent,name,rname.getText(),serverIp);
        } catch (NotBoundException | SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } catch (SpielraumVollException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Raum voll","Raum ist voll","Dieser Spielraum ist voll.");
        } catch (RaumNotExistException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Raum gibt es nicht mehr","Raum gibt es nicht mehr","Diesen Raum gibt es nicht mehr.");
        } catch (SpielLauftBereitsException e) {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR,"Spiel läuft","Spiel läuft schon","Der Raum hat das Spiel bereits begonnen.");
        }

    }
}
