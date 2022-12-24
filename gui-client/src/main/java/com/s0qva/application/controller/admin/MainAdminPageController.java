package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.controller.user.CommodityUserController;
import com.s0qva.application.fxml.FxmlPageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("main-admin-page.fxml")
@Component
public class MainAdminPageController implements Initializable {
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final FxmlPageLoader fxmlPageLoader;
    private final Class<CommodityAdminController> productAdminControllerClass;
    private final Class<CommodityUserController> productUserControllerClass;
    private final Class<OrderAdminController> orderAdminControllerClass;
    private final Class<UserAdminController> userAdminControllerClass;
    @FXML
    private HBox account;

    @Autowired
    public MainAdminPageController(DefaultUserAccountEventHandler defaultUserAccountEventHandler,
                                   FxmlPageLoader fxmlPageLoader) {
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.fxmlPageLoader = fxmlPageLoader;
        this.productAdminControllerClass = CommodityAdminController.class;
        this.productUserControllerClass = CommodityUserController.class;
        this.orderAdminControllerClass = OrderAdminController.class;
        this.userAdminControllerClass = UserAdminController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
    }

    public void onGoToAdminProductsPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(productAdminControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToProductsPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(productUserControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToAdminOrdersPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(orderAdminControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToAdminUsersPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(userAdminControllerClass);

        SceneSwitcher.switchScene(event, root);
    }
}
