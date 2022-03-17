package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.service.ProductService;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private ListView<ProductReadingDto> products;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ProductReadingDto> receivedProducts = productService.getAllProducts();
        products.setItems(FXCollections.observableArrayList(receivedProducts));
        addDoubleMouseClickedEventHandler();
    }

    private void addDoubleMouseClickedEventHandler() {
        products.setOnMouseClicked((click) -> {
            if (click.getClickCount() >= 2) {
                ProductReadingDto selectedProduct = products.getSelectionModel().getSelectedItem();
                ProductDetailsReadingDto productDetails = selectedProduct.getDetails();

                String description = "There is no any description";
                String madeIn = "Unknown";

                if (productDetails.getId() != null) {
                    description = productDetails.getDescription();
                    madeIn = productDetails.getMadeIn().getFullName();
                }

                String content = "Description: " + description + " \nMade in: " + madeIn;
                showDetailsInformationAlert(content);
            }
        });
    }

    private void showDetailsInformationAlert(String content) {
        AlertUtil.generateInformationAlert(
                DefaultAlertValue.INFO_ALERT_TITLE,
                DefaultAlertValue.INFO_ALERT_HEADER,
                content
        );
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_TITLE = "Details";
        private static final String INFO_ALERT_HEADER = "Here is details about the product";
    }
}
