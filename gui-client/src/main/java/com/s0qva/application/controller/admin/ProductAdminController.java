package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.user.OrderUserController;
import com.s0qva.application.controller.ProductController;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.ProductService;
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

@FxmlView("products-admin-page.fxml")
@Component
public class ProductAdminController extends ProductController implements Initializable {
    private final Class<OrderUserController> orderUserControllerClass;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    @FXML
    private ListView<ProductReadingDto> products;
    @FXML
    private ListView<ProductReadingDto> productsInCart;

    @Autowired
    public ProductAdminController(ProductService productService, FxmlPageLoader fxmlPageLoader) {
        super(productService, fxmlPageLoader);
        this.orderUserControllerClass = OrderUserController.class;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ProductReadingDto> receivedProducts = getProductService().getAllProducts();
        products.setItems(FXCollections.observableArrayList(receivedProducts));
        productsInCart.setItems(FXCollections.observableArrayList(getCart().getProducts()));
        showProductDetailsOnDoubleMouseClicked(products, productsInCart);
    }

    public void addToCart() {
        ProductReadingDto selectedProduct = products.getSelectionModel().getSelectedItem();
        getCart().addToCart(selectedProduct);
        productsInCart.setItems(FXCollections.observableArrayList(getCart().getProducts()));
    }

    public void removeFromCart() {
        ProductReadingDto selectedProduct = productsInCart.getSelectionModel().getSelectedItem();
        getCart().removeFromCart(selectedProduct);
        productsInCart.setItems(FXCollections.observableArrayList(getCart().getProducts()));
    }

    public void onCreateOrder(ActionEvent event) {
        Parent root = getFxmlPageLoader().loadFxmlFile(orderUserControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        Parent root = getFxmlPageLoader().loadFxmlFile(mainAdminPageControllerClass);
        SceneSwitcher.switchScene(event, root);
    }
}