package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.controller.user.ProductUserController;
import com.s0qva.application.fxml.FxmlPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private final Class<ProductUserController> productUserControllerClass;
    private final Class<OrderAdminController> orderAdminControllerClass;
    private final Class<UserAdminController> userAdminControllerClass;
    private final FxmlPageLoader fxmlPageLoader;
    @FXML
    private HBox account;

    @Autowired
    public MainAdminPageController(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.productAdminControllerClass = ProductAdminController.class;
        this.productUserControllerClass = ProductUserController.class;
        this.orderAdminControllerClass = OrderAdminController.class;
        this.userAdminControllerClass = UserAdminController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DefaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
    }

    public void onGoToAdminProductsPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToProductsPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productUserControllerClass);
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
}
