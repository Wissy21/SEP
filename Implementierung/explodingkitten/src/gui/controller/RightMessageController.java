package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RightMessageController {
    @FXML
    public Text message;
    @FXML
    public Text sendTime;
    @FXML
    public Text benutzername;

    public void set(String m, String s, String t){
        message.setText(m);
        benutzername.setText(s);
        sendTime.setText(t);
    }

}
