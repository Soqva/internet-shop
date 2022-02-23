package com.s0qva.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiClientApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/home-page.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Internet-shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
