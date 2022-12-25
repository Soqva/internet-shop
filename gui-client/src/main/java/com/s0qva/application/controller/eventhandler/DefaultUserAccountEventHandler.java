package com.s0qva.application.controller.eventhandler;

import com.s0qva.application.controller.LoginController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static javafx.scene.input.MouseButton.PRIMARY;
import static javafx.scene.input.MouseButton.SECONDARY;

@Component
public class DefaultUserAccountEventHandler {
    private final FxmlPageLoader fxmlPageLoader;
    private final Class<LoginController> loginController;

    @Autowired
    public DefaultUserAccountEventHandler(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.loginController = LoginController.class;
    }

    public void addEventHandlerToShowUserAccount(HBox account) {
        var user = UserSession.getInstance();
        var username = (Label) account.getChildren().get(0);

        username.setText(user.getUsername());
        account.setOnMouseClicked((event) -> {
            var mouseButton = event.getButton();

            if (mouseButton == PRIMARY) {
                String content = "логин: " + user.getUsername()
                        + "\nполное имя: " + user.getFirstName() + " " + user.getLastName();

                AlertUtil.generateInformationAlert(
                        DefaultAlertValue.INFO_ALERT_ACCOUNT_TITLE,
                        DefaultAlertValue.INFO_ALERT_ACCOUNT_HEADER,
                        content
                );
            }
            if (mouseButton == SECONDARY) {
                var root = fxmlPageLoader.loadFxmlFile(loginController);

                SceneSwitcher.switchScene(event, root);
                user.closeUserSession();
            }
        });
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_ACCOUNT_TITLE = "Аккаунт";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Информация обо мне";
    }
}
