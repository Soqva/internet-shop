package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("main-admin-page.fxml")
@Component
public class MainAdminPageController implements Initializable {
    private final Class<ProductAdminController> productAdminControllerClass;
    private final Class<OrderAdminController> orderAdminControllerClass;
    private final Class<UserAdminController> userAdminControllerClass;
    private final FxmlPageLoader fxmlPageLoader;
    @FXML
    private HBox account;

    @Autowired
    public MainAdminPageController(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.productAdminControllerClass = ProductAdminController.class;
        this.orderAdminControllerClass = OrderAdminController.class;
        this.userAdminControllerClass = UserAdminController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEventToShowUserAccount();
    }

    public void onGoToAdminProductsPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToAdminOrdersPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(orderAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToAdminUsersPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(userAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private void addEventToShowUserAccount() {
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
