package com.s0qva.application.fxml;

import javafx.scene.Parent;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FxmlPageLoader {
    private final FxWeaver fxWeaver;

    @Autowired
    public FxmlPageLoader(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    public Parent loadFxmlFile(Class<?> controllerClass) {
        return fxWeaver.loadView(controllerClass);
    }
}
