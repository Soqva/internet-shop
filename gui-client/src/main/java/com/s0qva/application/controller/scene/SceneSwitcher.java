package com.s0qva.application.controller.scene;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SceneSwitcher {

    public void switchScene(Event event, Parent root) {
        var stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        var scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
