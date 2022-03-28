package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.ProductService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import lombok.Data;

@Data
public abstract class ProductController {
    private final ProductService productService;
    private final FxmlPageLoader fxmlPageLoader;
    private final Cart cart;

    public ProductController(ProductService productService, FxmlPageLoader fxmlPageLoader) {
        this.productService = productService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.cart = Cart.getInstance();
    }

    public void addEventToShowProductDetails(ListView<ProductReadingDto>... listOfProducts) {
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
                            DefaultAlertValue.INFO_ALERT_PRODUCT_DETAILS_TITLE,
                            DefaultAlertValue.INFO_ALERT_PRODUCT_DETAILS_HEADER,
                            content
                    );
                }
            });
        }
    }

    public void addEventToShowUserAccount(HBox account) {
        UserSession user = UserSession.getInstance();
        Label username = (Label) account.getChildren().get(0);
        username.setText(user.getUsername());

        account.setOnMouseClicked((click) -> {


            String content = "username: " + user.getUsername()
                    + "\nfull name: " + user.getFirstName() + " " + user.getLastName()
                    + "\namount of orders: " + user.getOrders().size();

            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_TITLE,
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_HEADER,
                    content
            );
        });
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_PRODUCT_DETAILS_TITLE = "Details";
        private static final String INFO_ALERT_PRODUCT_DETAILS_HEADER = "Here is details about the product";
        private static final String INFO_ALERT_ACCOUNT_TITLE = "My account";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Here is information about me";
    }
}
