package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.UserRole;
import com.s0qva.application.service.RegistrationService;
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
@FxmlView("registration-page.fxml")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final FxmlPageLoader fxmlPageLoader;
    private final Class<LoginController> loginControllerClass;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    @Autowired
    public RegistrationController(RegistrationService registrationService, FxmlPageLoader fxmlPageLoader) {
        this.registrationService = registrationService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.loginControllerClass = LoginController.class;
    }

    public void onSignUp(ActionEvent event) {
        UserCreationDto userCreationDto = buildUserCreationDto();
        boolean isCreated = registrationService.signUp(userCreationDto);

        if (isCreated) {
            Parent root = fxmlPageLoader.loadFxmlFile(loginControllerClass);
            SceneSwitcher.switchScene(event, root);

            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_TITLE,
                    DefaultAlertValue.INFO_ALERT_HEADER,
                    DefaultAlertValue.INFO_ALERT_CONTENT
            );
        } else {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
        }
    }

    public void onBackToLogin(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(loginControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private UserCreationDto buildUserCreationDto() {
        return UserCreationDto.builder()
                .username(username.getText())
                .password(password.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .role(UserRole.USER)
                .banned(false)
                .build();
    }

    private static class DefaultAlertValue {
        private static final String ERROR_ALERT_TITLE = "Registration error";
        private static final String ERROR_ALERT_HEADER = "The username already exists";
        private static final String ERROR_ALERT_CONTENT = "User with this username already exists. Please," +
                " enter a unique username";
        private static final String INFO_ALERT_TITLE = "Successful registration";
        private static final String INFO_ALERT_HEADER = "Registration has been completed successfully. Please," +
                " sign in now to continue.";
        private static final String INFO_ALERT_CONTENT = "";
    }
}
