package com.s0qva.application.controller.eventhandler;

import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultUserAccountEventHandler {

    public void addEventHandlerToShowUserAccount(HBox account) {
        UserSession user = UserSession.getInstance();
        Label username = (Label) account.getChildren().get(0);
        username.setText(user.getUsername());

        account.setOnMouseClicked((click) -> {
            String content = "username: " + user.getUsername()
                    + "\nfull name: " + user.getFirstName() + " " + user.getLastName()
                    + "\namount of orders: " + user.getOrders().size();

            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_TITLE,
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_HEADER,
                    content
            );
        });
    }

    private class DefaultAlertValue {
        private static final String INFO_ALERT_ACCOUNT_TITLE = "My account";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Here is information about me";
    }
}
