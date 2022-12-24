package com.s0qva.application.controller.user;

import com.s0qva.application.controller.CommodityController;
import com.s0qva.application.controller.admin.MainAdminPageController;
import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.CommodityService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.s0qva.application.model.enumeration.UserRole.ADMIN;

@Component
@FxmlView("commodity-page.fxml")
public class CommodityUserController extends CommodityController implements Initializable {
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final Class<OrderUserController> orderUserControllerClass;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    @FXML
    private ListView<CommodityDto> commodities;
    @FXML
    private ListView<CommodityDto> commoditiesInCart;
    @FXML
    private HBox account;
    @FXML
    private Button backToMainAdminPageButton;

    @Autowired
    public CommodityUserController(CommodityService commodityService,
                                   FxmlPageLoader fxmlPageLoader,
                                   DefaultUserAccountEventHandler defaultUserAccountEventHandler) {
        super(commodityService, fxmlPageLoader);
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.orderUserControllerClass = OrderUserController.class;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
        showBackToMainAdminPageButton();
        fillProducts();
        fillProductsInCart();
        addEventToShowProductDetails(commodities, commoditiesInCart);
    }

    public void addToCart() {
        var selectedCommodity = commodities.getSelectionModel().getSelectedItem();

        cart.addToCart(selectedCommodity);
        commoditiesInCart.setItems(FXCollections.observableArrayList(cart.getCommodities()));
    }

    public void removeFromCart() {
        var selectedProduct = commoditiesInCart.getSelectionModel().getSelectedItem();

        cart.removeFromCart(selectedProduct);
        commoditiesInCart.setItems(FXCollections.observableArrayList(cart.getCommodities()));
    }

    public void onCreateOrder(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(orderUserControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    private void fillProducts() {
        var receivedProducts = commodityService.getAll();

        commodities.setItems(FXCollections.observableArrayList(receivedProducts));
    }

    private void fillProductsInCart() {
        commoditiesInCart.setItems(FXCollections.observableArrayList(cart.getCommodities()));
    }

    private void showBackToMainAdminPageButton() {
        if (userSession.containsRole(ADMIN)) {
            backToMainAdminPageButton.setVisible(true);
        }
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);

        SceneSwitcher.switchScene(event, root);
    }
}
