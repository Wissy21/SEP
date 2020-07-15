package main.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RightMessageController {
    @FXML
    public Text message;
    @FXML
    public Text sendTime;
    @FXML
    public Text benutzername;
    /**
     * Initialisier eine rechtsseitige Nachricht
     * @param m eigentliche Nachricht
     * @param s Name des Nutzers der die Nachricht geschrieben hat
     * @param t Zeitstempel
     */
    public void set(String m, String s, String t){
        message.setText(m);
        benutzername.setText(s);
        sendTime.setText(t);
    }

}
