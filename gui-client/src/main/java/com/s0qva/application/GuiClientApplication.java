package com.s0qva.application;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuiClientApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}
