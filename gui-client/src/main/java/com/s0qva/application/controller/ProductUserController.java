package com.s0qva.application.controller;

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

@Component
@FxmlView("products-page.fxml")
public class ProductUserController extends ProductController implements Initializable {
    private final Class<OrderUserController> orderUserControllerClass;
    @FXML
    private ListView<ProductReadingDto> products;
    @FXML
    private ListView<ProductReadingDto> productsInCart;

    @Autowired
    public ProductUserController(ProductService productService, FxmlPageLoader fxmlPageLoader) {
        super(productService, fxmlPageLoader);
        this.orderUserControllerClass = OrderUserController.class;
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
}
