package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.fxml.FxmlPageLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FxmlView("main-admin-page.fxml")
@Component
public class MainAdminPageController {
    private final Class<ProductAdminController> productAdminControllerClass;
    private final Class<OrderAdminController> orderAdminControllerClass;
    private final FxmlPageLoader fxmlPageLoader;

    @Autowired
    public MainAdminPageController(FxmlPageLoader fxmlPageLoader) {
        this.fxmlPageLoader = fxmlPageLoader;
        this.productAdminControllerClass = ProductAdminController.class;
        this.orderAdminControllerClass = OrderAdminController.class;
    }

    public void onGoToAdminProductsPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onGoToAdminOrdersPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(orderAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }
}
