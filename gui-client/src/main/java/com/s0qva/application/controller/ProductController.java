package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.ProductService;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.ListView;
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

    public void showProductDetailsOnDoubleMouseClicked(ListView<ProductReadingDto>... listOfProducts) {
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

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Details";
        private static final String INFO_ALERT_HEADER = "Here is details about the product";
    }
}
