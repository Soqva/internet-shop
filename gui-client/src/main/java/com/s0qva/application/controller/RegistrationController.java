package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.exception.FxmlPageLoadingException;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.UserRole;
import com.s0qva.application.service.RegistrationService;
import com.s0qva.application.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class RegistrationController {
    private final RegistrationService registrationService;
    private final FxmlPageLoader fxmlPageLoader;
    @Value("${gui-client.path.page.products}")
    private String pathToProductPage;
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
    }

    public void onSignUp(ActionEvent event) {
        UserCreationDto userCreationDto = buildUserCreationDto();
        boolean isCreated = registrationService.signUp(userCreationDto);

        if (isCreated) {
            Optional<Parent> maybeParent = fxmlPageLoader.loadFxmlFile(pathToProductPage);
            Parent parent = maybeParent.orElseThrow(() -> new FxmlPageLoadingException(pathToProductPage + " wasn't loaded"));
            SceneSwitcher.switchScene(event, parent);
        } else {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
        }
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
        private static final String ERROR_ALERT_CONTENT = "User with this username already exists. Please, enter a unique username";
    }
}
