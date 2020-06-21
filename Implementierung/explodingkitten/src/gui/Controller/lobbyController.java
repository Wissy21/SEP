package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

public class lobbyController {
    @FXML
    public TextField messageField;
    

    public void message(InputMethodEvent inputMethodEvent) {

        clearFields();
    }
    private void clearFields() {
        messageField.clear();

    }


    public void onInput(ActionEvent actionEvent) {
    }

    public void onInputText(InputMethodEvent inputMethodEvent) {
    }

    public void sendmessage(MouseEvent mouseEvent) {
    }

    public void zurückLobby(ActionEvent actionEvent) {
    }

    public void raum1Beitreten(ActionEvent actionEvent) {
    }

    public void raum2Beitreten(ActionEvent actionEvent) {
    }

    public void raum3Beitreten(ActionEvent actionEvent) {
    }
}
