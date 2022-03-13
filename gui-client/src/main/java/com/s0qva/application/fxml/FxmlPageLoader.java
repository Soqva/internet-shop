package com.s0qva.application.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class FxmlPageLoader {

    public Optional<Parent> loadFxmlFile(String pathToFxmlFile) {
        try {
            return Optional.ofNullable(FXMLLoader.load(getClass().getResource(pathToFxmlFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
