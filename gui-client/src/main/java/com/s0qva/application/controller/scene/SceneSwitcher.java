package com.s0qva.application.controller.scene;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SceneSwitcher {

    public void switchScene(ActionEvent event, Parent root) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
