package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.LoginService;
import com.s0qva.application.util.AlertUtil;
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
    private final LoginService loginService;
    private final Class<ProductController> productControllerClass;
    private final Class<RegistrationController> registrationControllerClass;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Autowired
    public LoginController(FxmlPageLoader fxmlPageLoader, LoginService loginService) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.loginService = loginService;
        this.productControllerClass = ProductController.class;
        this.registrationControllerClass = RegistrationController.class;
    }

    public void onSignIn(ActionEvent event) {
        UserAuthenticationDto userAuthenticationDto = buildUserAuthenticationDto();
        boolean isOk = loginService.signIn(userAuthenticationDto);

        if (isOk) {
            Parent root = fxmlPageLoader.loadFxmlFile(productControllerClass);
            SceneSwitcher.switchScene(event, root);
        } else {
            AlertUtil.generateErrorAlert(
                DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
        }
    }

    public void onSignUp(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(registrationControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private UserAuthenticationDto buildUserAuthenticationDto() {
        return UserAuthenticationDto.builder()
                .username(username.getText())
                .password(password.getText())
                .build();
    }

    private static class DefaultAlertValue {
        private static final String ERROR_ALERT_TITLE = "Sign in error";
        private static final String ERROR_ALERT_HEADER = "The entered information is wrong";
        private static final String ERROR_ALERT_CONTENT = "Username or password is incorrect. Please," +
                " check the data or sign up if you do not have an account";
    }
}
