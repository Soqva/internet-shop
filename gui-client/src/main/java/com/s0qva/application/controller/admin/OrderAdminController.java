package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.OrderController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.OrderStatus;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@FxmlView("orders-admin-page.fxml")
public class OrderAdminController extends OrderController implements Initializable {
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private OrderReadingDto selectedOrderForChanges;
    @FXML
    private ListView<OrderReadingDto> userOrders;
    @FXML
    private HBox account;
    @FXML
    private VBox windowForChanges;
    @FXML
    private ComboBox<OrderStatus> statusOrderComboBox;

    @Autowired
    public OrderAdminController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        super(orderService, fxmlPageLoader);
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillStatusOrderComboBox();
        addMouseClickedEventsOnUserOrders();
        addEventToShowUserAccount(account);
    }

    public void onReceiveAllOrders() {
        List<OrderReadingDto> orders = getOrderService().getAllOrders();
        userOrders.setItems(FXCollections.observableArrayList(orders));
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        Parent root = getFxmlPageLoader().loadFxmlFile(mainAdminPageControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onConfirmStatusChange() {
        OrderStatus selectedOrderStatus = statusOrderComboBox.getSelectionModel().getSelectedItem();
        selectedOrderForChanges.setStatus(selectedOrderStatus);
        OrderCreationDto updatingOrder = buildOrderCreationDto();
        OrderReadingDto updatedOrder = getOrderService().updateOrderStatus(selectedOrderForChanges.getId(), updatingOrder);
        userOrders.getItems().remove(selectedOrderForChanges);
        userOrders.getItems().add(updatedOrder);
        windowForChanges.setVisible(false);
    }

    private void fillStatusOrderComboBox() {
        statusOrderComboBox.setItems(FXCollections.observableArrayList(OrderStatus.values()));
    }

    private void addMouseClickedEventsOnUserOrders() {
        userOrders.setOnMouseClicked((click) -> {
            MouseButton button = click.getButton();

            if (button == MouseButton.PRIMARY && click.getClickCount() >= 2) {
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

            if (button == MouseButton.SECONDARY) {
                selectedOrderForChanges = userOrders.getSelectionModel().getSelectedItem();
                OrderStatus orderStatus = selectedOrderForChanges.getStatus();
                statusOrderComboBox.setValue(orderStatus);
                windowForChanges.setVisible(true);
            }
        });
    }

    private OrderCreationDto buildOrderCreationDto() {
        List<ProductIdDto> productIdDtos = selectedOrderForChanges.getProducts().stream()
                .map(ProductReadingDto::getId)
                .map(ProductIdDto::new)
                .collect(Collectors.toList());

        return OrderCreationDto.builder()
                .orderDate(selectedOrderForChanges.getOrderDate())
                .status(selectedOrderForChanges.getStatus())
                .userId(selectedOrderForChanges.getUserId())
                .products(productIdDtos)
                .build();
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Order information";
        private static final String INFO_ALERT_HEADER = "Here is information about the order";
    }
}
