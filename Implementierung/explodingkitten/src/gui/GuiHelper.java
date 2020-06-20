package gui;

import javafx.scene.control.Alert;

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
}
