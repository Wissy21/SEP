package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import server.Spieler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class GuiHelper {
    public static void showErrorOrWarningAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.setHeight(500);
        alert.setWidth(400);
        alert.showAndWait();
    }

    public static Spieler showSpielerSelect(ArrayList<Spieler> choice) {
        ChoiceDialog<Spieler> dialog = new ChoiceDialog<>(choice.get(0),choice);
        dialog.setOnCloseRequest(e->{
            Platform.runLater(()->GuiHelper.showErrorOrWarningAlert(Alert.AlertType.WARNING,"Bitte etwas auswählen","Bitte etwas auswählen","Sie müssen einen anderen Spieler auswählen."));});
        Optional<Spieler> result = dialog.showAndWait();
        return result.get();
    }
}