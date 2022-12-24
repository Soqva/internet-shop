package com.s0qva.application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlertUtil {

    public void generateErrorAlert(String title, String headerText, String contentText) {
        var alert = createAlert(title, headerText, contentText, Alert.AlertType.ERROR);

        alert.showAndWait();
    }

    public void generateInformationAlert(String title, String headerText, String contentText) {
        var alert = createAlert(title, headerText, contentText, Alert.AlertType.INFORMATION);

        alert.showAndWait();
    }

    private Alert createAlert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        var alert = new Alert(alertType);
        var dialogPaneContent = new VBox();
        var contentLabel = new Label();

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        contentLabel.setText(contentText);
        dialogPaneContent.getChildren().add(contentLabel);
        alert.getDialogPane().setContent(dialogPaneContent);
        return alert;
    }

}
