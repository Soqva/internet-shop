package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.OrderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("orders-admin-page.fxml")
public class OrderAdminController extends OrderController implements Initializable {
    private final Class<ProductAdminController> productAdminControllerClass;
    @FXML
    private ListView<OrderReadingDto> userOrders;

    @Autowired
    public OrderAdminController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        super(orderService, fxmlPageLoader);
        this.productAdminControllerClass = ProductAdminController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onBackToProducts(ActionEvent event) {
        Parent root = getFxmlPageLoader().loadFxmlFile(productAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }
}
