package com.s0qva.application.controller.eventhandler;

import com.s0qva.application.controller.LoginController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserAccountEventHandler {
    private final Class<LoginController> loginController;
    private final FxmlPageLoader fxmlPageLoader;

    @Autowired
    public DefaultUserAccountEventHandler(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.loginController = LoginController.class;
    }

    public void addEventHandlerToShowUserAccount(HBox account) {
        UserSession user = UserSession.getInstance();
        Label username = (Label) account.getChildren().get(0);
        username.setText(user.getUsername());

        account.setOnMouseClicked((event) -> {
            MouseButton mouseButton = event.getButton();

            if (mouseButton == MouseButton.PRIMARY) {
                String content = "username: " + user.getUsername()
                        + "\nfull name: " + user.getFirstName() + " " + user.getLastName()
                        + "\namount of orders: " + user.getOrders().size();

                AlertUtil.generateInformationAlert(
                        DefaultAlertValue.INFO_ALERT_ACCOUNT_TITLE,
                        DefaultAlertValue.INFO_ALERT_ACCOUNT_HEADER,
                        content
                );
            }
            if (mouseButton == MouseButton.SECONDARY) {
                user.closeUserSession();
                Parent root = fxmlPageLoader.loadFxmlFile(loginController);
                SceneSwitcher.switchScene(event, root);
            }
        });
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_ACCOUNT_TITLE = "My account";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Here is information about me";
    }
}
