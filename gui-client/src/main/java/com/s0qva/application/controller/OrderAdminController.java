package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
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
        showOrderDetailsOnDoubleMouseClicked();
    }

    public void onReceiveAllOrders() {
        List<OrderReadingDto> orders = getOrderService().getAllOrders();
        userOrders.setItems(FXCollections.observableArrayList(orders));
    }

    public void onBackToProducts(ActionEvent event) {
        Parent root = getFxmlPageLoader().loadFxmlFile(productAdminControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private void showOrderDetailsOnDoubleMouseClicked() {
        userOrders.setOnMouseClicked((click) -> {
            if (click.getClickCount() >= 2) {
                OrderReadingDto selectedOrder = userOrders.getSelectionModel().getSelectedItem();

                String content = "Order id: " + selectedOrder.getId() + "\n" +
                        "Order date: " + selectedOrder.getOrderDate() + "\n" +
                        "Order status: " + selectedOrder.getStatus() + "\n" +
                        "User id: " + selectedOrder.getUserId().getId() + "\n" +
                        "Order products: " + selectedOrder.getProducts() + "\n" +
                        "Total price: " + selectedOrder.getProducts().stream()
                            .mapToDouble(ProductReadingDto::getPrice)
                            .sum();

                AlertUtil.generateInformationAlert(
                        DefaultAlertValue.INFO_ALERT_TITLE,
                        DefaultAlertValue.INFO_ALERT_HEADER,
                        content
                );
            }
        });
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Order information";
        private static final String INFO_ALERT_HEADER = "Here is information about the order";
    }
}
