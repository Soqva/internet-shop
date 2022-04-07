package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.ProductService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.ListView;
import lombok.Data;

@Data
public abstract class ProductController {
    protected final ProductService productService;
    protected final FxmlPageLoader fxmlPageLoader;
    protected final UserSession userSession;
    protected final Cart cart;

    public ProductController(ProductService productService, FxmlPageLoader fxmlPageLoader) {
        this.productService = productService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.userSession = UserSession.getInstance();
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

                    if (productDetails.getDescription() != null) {
                        description = productDetails.getDescription();
                    }

                    if (productDetails.getMadeIn() != null) {
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

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_PRODUCT_DETAILS_TITLE = "Details";
        private static final String INFO_ALERT_PRODUCT_DETAILS_HEADER = "Here is details about the product";
    }
}
