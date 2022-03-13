package com.s0qva.application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlertUtil {

    public void generateErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);

        VBox dialogPaneContent = new VBox();

        Label contentLabel = new Label();
        contentLabel.setText(contentText);

        dialogPaneContent.getChildren().add(contentLabel);

        alert.getDialogPane().setContent(dialogPaneContent);

        alert.showAndWait();
    }
}
