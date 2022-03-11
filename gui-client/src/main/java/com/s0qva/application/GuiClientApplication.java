package com.s0qva.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GuiClientApplication extends Application {
    private ConfigurableApplicationContext context;
    private Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(GuiClientApplication.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration-page.fxml"));
        loader.setControllerFactory(context::getBean);
        root = loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Internet-shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }
}
