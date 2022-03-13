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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class RegistrationController {
    private final RegistrationService registrationService;
    private final FxmlPageLoader fxmlPageLoader;
    @FXML
    private TextField username;
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
        UserCreationDto userCreationDto = createUserCreationDto();
        boolean isCreated = registrationService.signUp(userCreationDto);

        if (isCreated) {
            Optional<Parent> maybeParent = fxmlPageLoader.loadFxmlFile("/fxml/products-page.fxml");
            Parent parent = maybeParent.orElseThrow(RuntimeException::new);
            SceneSwitcher.switchScene(event, parent);
        } else {
            AlertUtil.generateErrorAlert("Registration error",
                    "The username already exists",
                    "User with this username already exists. Please, enter a unique username");
        }
    }

    private UserCreationDto createUserCreationDto() {
        return UserCreationDto.builder()
                .username(username.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .role(UserRole.USER)
                .banned(false)
                .build();
    }
}
