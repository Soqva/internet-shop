package com.s0qva.application.controller;

import com.s0qva.application.controller.admin.MainAdminPageController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.controller.user.CommodityUserController;
import com.s0qva.application.dto.AuthDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.UserRole;
import com.s0qva.application.service.LoginService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.s0qva.application.model.enumeration.UserRole.ADMIN;

@Slf4j
@Component
@FxmlView("login-page.fxml")
public class LoginController {
    private final FxmlPageLoader fxmlPageLoader;
    private final LoginService loginService;
    private final Class<CommodityUserController> productUserControllerClass;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private final Class<RegistrationController> registrationControllerClass;
    private final UserSession userSession;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @Autowired
    public LoginController(FxmlPageLoader fxmlPageLoader, LoginService loginService) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.loginService = loginService;
        this.productUserControllerClass = CommodityUserController.class;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
        this.registrationControllerClass = RegistrationController.class;
        this.userSession = UserSession.getInstance();
    }

    public void onSignIn(ActionEvent event) {
        var authDto = buildAuthDto();
        var success = loginService.signIn(authDto);

        if (!success) {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
            return;
        }
        if (UserSession.getInstance().isBlocked()) {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_BAN_HEADER,
                    DefaultAlertValue.ERROR_ALERT_BAN_CONTENT
            );
            return;
        }
        var root = (Parent) null;

        if (userSession.containsRole(ADMIN)) {
            root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);
        } else {
            root = fxmlPageLoader.loadFxmlFile(productUserControllerClass);
        }
        SceneSwitcher.switchScene(event, root);
    }

    public void onSignUp(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(registrationControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    private AuthDto buildAuthDto() {
        return AuthDto.builder()
                .username(username.getText())
                .password(password.getText())
                .build();
    }

    private static class DefaultAlertValue {
        private static final String ERROR_ALERT_TITLE = "Sign in error";
        private static final String ERROR_ALERT_HEADER = "The entered information is wrong";
        private static final String ERROR_ALERT_CONTENT = "Username or password is incorrect. Please," +
                " check the data or sign up if you do not have an account";
        private static final String ERROR_ALERT_BAN_HEADER = "Your account has been banned";
        private static final String ERROR_ALERT_BAN_CONTENT = "The store administrator has banned your account" +
                " for violating store rules";
    }
}
