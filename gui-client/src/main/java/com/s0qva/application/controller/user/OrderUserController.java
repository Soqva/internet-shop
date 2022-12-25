package com.s0qva.application.controller.user;

import com.s0qva.application.controller.OrderController;
import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.OrderStatus;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.s0qva.application.model.enumeration.OrderStatus.WAITING;
import static java.util.stream.Collectors.toList;

@Component
@FxmlView("orders-page.fxml")
public class OrderUserController extends OrderController implements Initializable {
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final Class<CommodityUserController> productUserControllerClass;
    @FXML
    private ListView<OrderDto> userOrders;
    @FXML
    private ListView<CommodityDto> userCurrentOrder;
    @FXML
    private HBox account;

    @Autowired
    public OrderUserController(OrderService orderService,
                               FxmlPageLoader fxmlPageLoader,
                               DefaultUserAccountEventHandler defaultUserAccountEventHandler) {
        super(orderService, fxmlPageLoader);
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.productUserControllerClass = CommodityUserController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
        fillUserOrders();
        fillCurrentOrder();
    }

    public void onCreateOrder(ActionEvent event) {
        var orderDto = buildOrderDto();
        var created = orderService.create(orderDto);

        if (!created) {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
            return;
        }
        cart.clearCart();

        var root = fxmlPageLoader.loadFxmlFile(OrderUserController.class);

        SceneSwitcher.switchScene(event, root);
        AlertUtil.generateInformationAlert(
                DefaultAlertValue.INFO_ALERT_TITLE,
                DefaultAlertValue.INFO_ALERT_HEADER,
                DefaultAlertValue.INFO_ALERT_CONTENT
        );
    }

    public void onBackToProducts(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(productUserControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private void fillUserOrders() {
        var userAllOrders = orderService.getAllByUserId(userSession.getId());

        userOrders.getItems().clear();
        userOrders.setItems(FXCollections.observableArrayList(userAllOrders));
    }

    private void fillCurrentOrder() {
        var buyingCommodities = cart.getCommodities();

        userCurrentOrder.setItems(FXCollections.observableArrayList(buyingCommodities));
    }

    private OrderDto buildOrderDto() {
        var initialOrderStatus = orderService.getStatuses().stream()
                .filter(currentOrderStatus -> currentOrderStatus.getName().equals(WAITING.getName()))
                .findFirst()
                .orElseThrow();
        var orderedAmountOfCommodities = new HashMap<CommodityDto, Integer>();

        cart.getCommodities().forEach(currentCommodityDto -> {
            orderedAmountOfCommodities.computeIfAbsent(currentCommodityDto, key -> 0);
            orderedAmountOfCommodities.computeIfPresent(currentCommodityDto, (key, amount) -> amount + 1);
        });
        orderedAmountOfCommodities.forEach(CommodityDto::setAmount);
        return OrderDto.builder()
                .orderStatus(initialOrderStatus)
                .orderedCommodities(cart.getCommodities().stream()
                        .distinct()
                        .collect(toList())
                )
                .user(UserSession.getInstance().getUser())
                .build();
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Successful order creation";
        private static final String INFO_ALERT_HEADER = "Your order has been successfully created!";
        private static final String INFO_ALERT_CONTENT = "The order will be handled by an administrator. " +
                "Please wait his decision. You can check the order's status in list of your orders";
        private static final String ERROR_ALERT_TITLE = "Order creation error";
        private static final String ERROR_ALERT_HEADER = "Your order hasn't been created";
        private static final String ERROR_ALERT_CONTENT = "Something went wrong during the order creation. " +
                "We would try to fix the problem";
    }
}
