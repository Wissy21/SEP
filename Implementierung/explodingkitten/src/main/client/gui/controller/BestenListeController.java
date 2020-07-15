package main.client.gui.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.server.datenbankmanager.DBinterface;
import main.server.datenbankmanager.Row;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class BestenListeController {
    @FXML
    TableColumn<Row,String> name;
    @FXML
    TableColumn<Row,Integer> platz;
    @FXML
    TableColumn<Row,Integer> punkte;
    @FXML
    TableView<Row> table;
    String username;
    String serverIp;

    /**
     * Nutzer wird zurück ins Menü geleitet
     * @param actionEvent Event das die Mwthode ausgelöst hat
     */
    public void zurueckBestenListe(ActionEvent actionEvent) {
        try {

            VueManager.goToMenue(actionEvent, username,serverIp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode die die Bestenliste von der Datenbank abfragt und darstellt
     * @param n Name des anfragenden Benutzers
     * @param ip Ip-Adresse des Servers
     */
    public void setName(String n,String ip) {
        username = n;
        this.serverIp = ip;
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://" + serverIp + ":1900/db");
            ObservableList<Row> rs = FXCollections.observableArrayList(db.getBestenliste());
            name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Row, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Row, String> r) {
                    return r.getValue().nameProperty();
                }
            });
            platz.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Row, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Row, Integer> r) {
                    return r.getValue().platzProperty().asObject();
                }
            });
            punkte.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Row, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Row, Integer> r) {
                    return r.getValue().punkteProperty().asObject();
                }
            });
            table.setItems(rs);
        } catch (NotBoundException | MalformedURLException | RemoteException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
