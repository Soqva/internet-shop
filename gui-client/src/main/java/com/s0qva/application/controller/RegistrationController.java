package com.s0qva.application.controller;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.model.enumeration.UserRole;
import com.s0qva.application.service.RegistrationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationController {
    private final RegistrationService registrationService;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public void onSignUp() {
        UserCreationDto userCreationDto = createUserCreationDto();
        boolean isCreated = registrationService.signUp(userCreationDto);
        log.info("isCreated? " + isCreated);
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
