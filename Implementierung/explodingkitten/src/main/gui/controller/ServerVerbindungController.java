package main.gui.controller;

import main.gui.GuiHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.rmi.Naming;

public class ServerVerbindungController {
    @FXML
    public TextField serverIp;
    @FXML
    public Button verbinden;


    @FXML
    public void verbinden(ActionEvent actionEvent) {
        String ip = serverIp.getText().trim();
        if (!ip.isEmpty()) {
            try {
                Naming.lookup("rmi://" + ip + ":1900/db");
                VueManager.goToStartFenster(actionEvent, ip);
            } catch (Throwable throwable) {
                GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING, "Server IP Error", "Server IP Address is wrong", "Please type in a valid ip address");
            }
        } else {
            GuiHelper.showErrorOrWarningAlert(Alert.AlertType.ERROR, "Server IP Missing", "Server IP Address is missing", "Please type in the ip address of your main.server");
        }
    }
}
