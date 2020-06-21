package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

public class lobbyController {
    @FXML
    public TextField messageField;

    public void Spielraum1(MouseEvent mouseEvent) {
    }

    public void Spileraum3(MouseEvent mouseEvent) {
    }

    public void Spielraum2(MouseEvent mouseEvent) {
    }

    public void message(InputMethodEvent inputMethodEvent) {

        clearFields();
    }
    private void clearFields() {
        messageField.clear();

    }

    public void sendmessage(MouseEvent mouseEvent) {
    }

    public void sendButton(ActionEvent actionEvent) {
    }
}
