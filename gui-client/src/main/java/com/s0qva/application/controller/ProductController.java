package com.s0qva.application.controller;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.ProductService;
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
@FxmlView("products-page.fxml")
public class ProductController implements Initializable {
    private final ProductService productService;
    private final FxmlPageLoader fxmlPageLoader;
    private final Class<OrderController> orderControllerClass;
    private final Cart cart;
    @FXML
    private ListView<ProductReadingDto> products;
    @FXML
    private ListView<ProductReadingDto> productsInCart;

    @Autowired
    public ProductController(ProductService productService, FxmlPageLoader fxmlPageLoader) {
        this.productService = productService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.orderControllerClass = OrderController.class;
        this.cart = Cart.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ProductReadingDto> receivedProducts = productService.getAllProducts();
        products.setItems(FXCollections.observableArrayList(receivedProducts));
        productsInCart.setItems(FXCollections.observableArrayList(cart.getProducts()));
        showProductDetailsOnDoubleMouseClicked(products, productsInCart);
    }

    @SafeVarargs
    private void showProductDetailsOnDoubleMouseClicked(ListView<ProductReadingDto>... listOfProducts) {
        for (ListView<ProductReadingDto> currentList : listOfProducts) {
            currentList.setOnMouseClicked((click) -> {
                if (click.getClickCount() >= 2) {
                    ProductReadingDto selectedProduct = currentList.getSelectionModel().getSelectedItem();
                    ProductDetailsReadingDto productDetails = selectedProduct.getDetails();

                    String description = "There is no any description";
                    String madeIn = "Unknown";

                    if (productDetails.getId() != null) {
                        description = productDetails.getDescription();
                        madeIn = productDetails.getMadeIn().getFullName();
                    }

                    String content = "Description: " + description + " \nMade in: " + madeIn;

                    AlertUtil.generateInformationAlert(
                            DefaultAlertValue.INFO_ALERT_TITLE,
                            DefaultAlertValue.INFO_ALERT_HEADER,
                            content
                    );
                }
            });
        }
    }

    public void addToCartOnMouseClicked() {
        ProductReadingDto selectedProduct = products.getSelectionModel().getSelectedItem();
        cart.addToCart(selectedProduct);
        productsInCart.setItems(FXCollections.observableArrayList(cart.getProducts()));
    }

    public void onCreateOrder(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(orderControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Details";
        private static final String INFO_ALERT_HEADER = "Here is details about the product";
    }
}
