package com.s0qva.application.controller.user;

import com.s0qva.application.controller.OrderController;
import com.s0qva.application.controller.admin.ProductAdminController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.enumeration.OrderStatus;
import com.s0qva.application.model.enumeration.UserRole;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@FxmlView("orders-page.fxml")
public class OrderUserController extends OrderController implements Initializable {
    private final Class<ProductUserController> productUserControllerClass;
    private final Class<ProductAdminController> productAdminControllerClass;
    @FXML
    private ListView<OrderReadingDto> userOrders;
    @FXML
    private ListView<ProductReadingDto> userCurrentOrder;

    @Autowired
    public OrderUserController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        super(orderService, fxmlPageLoader);
        this.productAdminControllerClass = ProductAdminController.class;
        this.productUserControllerClass = ProductUserController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userOrders.getItems().clear();
        List<OrderReadingDto> userAllOrders = getOrderService().getAllOrdersForSpecificUser(getUserSession().getId());
        userOrders.setItems(FXCollections.observableArrayList(userAllOrders));
        fillCurrentOrder();
    }

    public void fillCurrentOrder() {
        List<ProductReadingDto> buyingProducts = getCart().getProducts();
        userCurrentOrder.setItems(FXCollections.observableArrayList(buyingProducts));
    }

    public void onCreateOrder(ActionEvent event) {
        OrderCreationDto orderCreationDto = buildOrderCreationDto();
        boolean isCreated = getOrderService().createOrder(orderCreationDto);

        if (isCreated) {
            getCart().clearCart();

            Parent root = getFxmlPageLoader().loadFxmlFile(OrderUserController.class);
            SceneSwitcher.switchScene(event, root);

            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_TITLE,
                    DefaultAlertValue.INFO_ALERT_HEADER,
                    DefaultAlertValue.INFO_ALERT_CONTENT
            );
        } else {
            AlertUtil.generateErrorAlert(
                    DefaultAlertValue.ERROR_ALERT_TITLE,
                    DefaultAlertValue.ERROR_ALERT_HEADER,
                    DefaultAlertValue.ERROR_ALERT_CONTENT
            );
        }
    }

    private OrderCreationDto buildOrderCreationDto() {
        UserIdDto userIdDto = UserIdDto.builder()
                .id(getUserSession().getId())
                .build();

        List<ProductIdDto> buyingProductIdDtos = getCart().getProducts().stream()
                .map(ProductReadingDto::getId)
                .map(ProductIdDto::new)
                .collect(Collectors.toList());

        return OrderCreationDto.builder()
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.WAITING)
                .userId(userIdDto)
                .products(buyingProductIdDtos)
                .build();
    }

    public void onBackToProducts(ActionEvent event) {
        Parent root;
        if (getUserSession().getRole() == UserRole.ADMIN) {
            root = getFxmlPageLoader().loadFxmlFile(productAdminControllerClass);
        } else {
            root = getFxmlPageLoader().loadFxmlFile(productUserControllerClass);
        }
        SceneSwitcher.switchScene(event, root);
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
