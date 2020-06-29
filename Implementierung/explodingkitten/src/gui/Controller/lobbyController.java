package gui.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    @FXML
    public VBox roomList;

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
        VueManager.goToSpiel(actionEvent);
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


    public void createRoom(ActionEvent actionEvent) {
        /*try {
            LobbyInterface l = (LobbyInterface) Naming.lookup("rmi://localhost:1900/lobby");
            boolean check = l.addRoom(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        updateRoomList();
    }

    public void updateRoomList(){
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../Vue/LobbyRoom.fxml"));

        try {
            Parent root = fxmlLoader.load();

            //LobbyRoom lb = fxmlLoader.getController();
            //lb.setRaum(this, sr.id, sr.playerList.size(), sr.started);
            Platform.runLater(() -> this.roomList.getChildren().add(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
