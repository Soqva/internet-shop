package com.s0qva.application.controller;

import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Data;

@Data
public abstract class OrderController {
    private final OrderService orderService;
    private final FxmlPageLoader fxmlPageLoader;
    private final UserSession userSession;
    private final Cart cart;

    public OrderController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        this.orderService = orderService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.userSession = UserSession.getInstance();
        this.cart = Cart.getInstance();
    }

    public void addEventToShowUserAccount(HBox account) {
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

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_ACCOUNT_TITLE = "My account";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Here is information about me";
    }
}
