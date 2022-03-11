package com.s0qva.application;

import com.s0qva.application.config.ComponentConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class GuiClientApplication extends Application {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ComponentConfiguration.class)) {
            launch(args);
        };
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
