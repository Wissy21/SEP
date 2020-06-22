package gui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static gui.GuiHelper.showErrorOrWarningAlert;

public class lobbyController {
    @FXML
    public TextField messageField;
    @FXML
    public Label raum1Label1;
    @FXML
    public Label raum1Label2;
    @FXML
    public Label raum1Label3;
    @FXML
    public Label raum1Label4;
    @FXML
    public Label raum2Label1;
    @FXML
    public Label raum2Label2;
    @FXML
    public Label raum2Label3;
    @FXML
    public Label raum2Label4;
    @FXML
    public Label raum3Label1;
    @FXML
    public Label raum3Label2;
    @FXML
    public Label raum3Label3;
    @FXML
    public Label raum3Label4;

    @FXML
    public VBox eintragRaum1;
    @FXML
    public VBox eintragRaum2;
    @FXML
    public VBox eintragRaum3;

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
        showErrorOrWarningAlert(Alert.AlertType.INFORMATION, "Textfields are empty","Eingabefehler"," Bitte,Geben Sie Ihre Anmeldedaten ein");

    }

    public void zurückLobby(ActionEvent actionEvent) throws IOException {
        VueManager.goToLobby(actionEvent);
    }

    public void raum1Beitreten(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpielraum(actionEvent);
    }

    public void raum2Beitreten(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpielraum(actionEvent);
    }

    public void raum3Beitreten(ActionEvent actionEvent) throws IOException {
        VueManager.goToSpielraum(actionEvent);
    }


}
