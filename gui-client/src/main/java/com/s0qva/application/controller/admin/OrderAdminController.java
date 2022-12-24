package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.OrderController;
import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.MouseButton.PRIMARY;
import static javafx.scene.input.MouseButton.SECONDARY;

@Component
@FxmlView("orders-admin-page.fxml")
public class OrderAdminController extends OrderController implements Initializable {
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private OrderDto selectedOrderForChanges;
    @FXML
    private ListView<OrderDto> userOrders;
    @FXML
    private HBox account;
    @FXML
    private VBox windowForChanges;
    @FXML
    private ComboBox<DictionaryOrderStatusDto> statusOrderComboBox;

    @Autowired
    public OrderAdminController(OrderService orderService,
                                FxmlPageLoader fxmlPageLoader,
                                DefaultUserAccountEventHandler defaultUserAccountEventHandler) {
        super(orderService, fxmlPageLoader);
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
        addEventHandlerToShowSelectedOrder();
        fillStatusOrderComboBox();
    }

    public void onReceiveAllOrders() {
        var orders = orderService.getAll();

        userOrders.setItems(FXCollections.observableArrayList(orders));
    }

    public void onConfirmStatusChange() {
        var selectedOrderStatus = statusOrderComboBox.getSelectionModel().getSelectedItem();

        selectedOrderForChanges.setOrderStatus(selectedOrderStatus);

        var updatingOrder = buildOrderDto();
        var updatedOrder = orderService.updateStatus(selectedOrderForChanges.getId(), updatingOrder);
        var replacementIndex = userOrders.getItems().indexOf(selectedOrderForChanges);

        userOrders.getItems().set(replacementIndex, updatedOrder);
        windowForChanges.setVisible(false);
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    private void fillStatusOrderComboBox() {
        statusOrderComboBox.setItems(FXCollections.observableArrayList(orderService.getStatuses()));
    }

    private void addEventHandlerToShowSelectedOrder() {
        userOrders.setOnMouseClicked(click -> {
            var button = click.getButton();

            if (button == PRIMARY && click.getClickCount() >= 2) {
                var selectedOrder = userOrders.getSelectionModel().getSelectedItem();
                var content = "Order id: " + selectedOrder.getId() + "\n" +
                        "Order status: " + selectedOrder.getOrderStatus().getName() + "\n" +
                        "User id: " + selectedOrder.getUser().getId() + "\n" +
                        "Ordered commodities: " + selectedOrder.getOrderedCommodities() + "\n" +
                        "Total price: " + selectedOrder.getOrderCost();
                AlertUtil.generateInformationAlert(
                        DefaultAlertValue.INFO_ALERT_TITLE,
                        DefaultAlertValue.INFO_ALERT_HEADER,
                        content
                );
            }
            if (button == SECONDARY) {
                selectedOrderForChanges = userOrders.getSelectionModel().getSelectedItem();
                var orderStatus = selectedOrderForChanges.getOrderStatus();

                statusOrderComboBox.setValue(orderStatus);
                windowForChanges.setVisible(true);
            }
        });
    }

    private OrderDto buildOrderDto() {
        return OrderDto.builder()
                .user(UserSession.getInstance().getUser())
                .orderedCommodities(selectedOrderForChanges.getOrderedCommodities())
                .orderStatus(selectedOrderForChanges.getOrderStatus())
                .build();
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Order information";
        private static final String INFO_ALERT_HEADER = "Here is information about the order";
    }
}
