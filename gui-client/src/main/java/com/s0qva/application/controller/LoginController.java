package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.fxml.FxmlPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FxmlView("login-page.fxml")
public class LoginController {
    private final FxmlPageLoader fxmlPageLoader;
    private final Class<ProductController> productControllerClass;
    private final Class<RegistrationController> registrationControllerClass;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Autowired
    public LoginController(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.productControllerClass = ProductController.class;
        this.registrationControllerClass = RegistrationController.class;
    }

    public void onSignIn(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onSignUp(ActionEvent event) {
        log.info("onSignUp was called");
        Parent root = fxmlPageLoader.loadFxmlFile(registrationControllerClass);
        SceneSwitcher.switchScene(event, root);
    }
}
