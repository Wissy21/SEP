package gui.controller;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import server.datenbankmanager.DBinterface;
import server.datenbankmanager.Row;

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

    public void zurueckBestenListe(ActionEvent actionEvent) throws IOException {
        VueManager.goToMenue(actionEvent, username);
    }

    public void setName(String n) {
        username = n;
        try {
            DBinterface db = (DBinterface) Naming.lookup("rmi://localhost:1900/db");
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
