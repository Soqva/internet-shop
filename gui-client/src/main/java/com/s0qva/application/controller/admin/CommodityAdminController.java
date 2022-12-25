package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.CommodityController;
import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.dto.dictionary.DictionaryCountryDto;
import com.s0qva.application.dto.dictionary.DictionarySupplierDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.CommodityService;
import com.s0qva.application.service.SupplyService;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("commodity-admin-page.fxml")
@Component
public class CommodityAdminController extends CommodityController implements Initializable {
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private final SupplyService supplyService;
    @FXML
    private ListView<CommodityDto> commodities;
    @FXML
    private HBox account;
    @FXML
    private VBox windowsForCreating;
    @FXML
    private TextField supplierName;
    @FXML
    private TextField supplierDescription;
    @FXML
    private TextField commodityName;
    @FXML
    private TextField commodityCost;
    @FXML
    private TextField commodityDescription;
    @FXML
    private TextField suppliedCommodityAmount;
    @FXML
    private ComboBox<DictionaryCountryDto> commodityMadeInComboBox;

    @Autowired
    public CommodityAdminController(CommodityService commodityService,
                                    FxmlPageLoader fxmlPageLoader,
                                    DefaultUserAccountEventHandler defaultUserAccountEventHandler,
                                    SupplyService supplyService) {
        super(commodityService, fxmlPageLoader);
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
        this.supplyService = supplyService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
        addEventToShowProductDetails(commodities);
        fillCommodityMadeInComboBox();
    }

    public void onReceiveAllCommodities() {
        var receivedCommodities = commodityService.getAll();

        commodities.setItems(FXCollections.observableArrayList(receivedCommodities));
    }

    public void onCreateNewCommodity() {
        windowsForCreating.setVisible(true);
    }

    public void onConfirmCreatingProduct() {
        windowsForCreating.setVisible(false);

        var newCommodity = buildSupplyDto();
        var created = supplyService.create(newCommodity);

        if (created) {
            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_CREATED_PRODUCT_TITLE,
                    DefaultAlertValue.INFO_ALERT_CREATED_PRODUCT_HEADER,
                    DefaultAlertValue.INFO_ALERT_CREATED_PRODUCT_CONTENT
            );
            clearAllCommodityCreatingFields();
            onReceiveAllCommodities();
        }
    }

    public void onCancelCreatingCommodity() {
        windowsForCreating.setVisible(false);
        clearAllCommodityCreatingFields();
    }

    private void fillCommodityMadeInComboBox() {
        commodityMadeInComboBox.getItems().addAll(commodityService.getProducingCountries());
    }

    private void clearAllCommodityCreatingFields() {
        commodityName.clear();
        commodityCost.clear();
        commodityDescription.clear();
        commodityMadeInComboBox.getSelectionModel().clearSelection();
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        var root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);

        SceneSwitcher.switchScene(event, root);
    }

    private SupplyDto buildSupplyDto() {
        var enteredSupplierName = supplierName.getText();
        var enteredSupplierDescription = supplierDescription.getText();
        var enteredCommodityName = commodityName.getText();
        var enteredCommodityDescription = commodityDescription.getText();
        var selectedCountry = commodityMadeInComboBox.getSelectionModel().getSelectedItem();
        var enteredCommodityCost = Double.parseDouble(commodityCost.getText());
        var enteredSuppliedAmount = Integer.parseInt(suppliedCommodityAmount.getText());

        return SupplyDto.builder()
                .supplier(DictionarySupplierDto.builder()
                        .name(enteredSupplierName)
                        .description(enteredSupplierDescription)
                        .build()
                )
                .receivingDate(System.currentTimeMillis())
                .suppliedCommodities(List.of(
                        CommodityDto.builder()
                                .name(enteredCommodityName)
                                .description(enteredCommodityDescription)
                                .producingCountry(selectedCountry)
                                .cost(enteredCommodityCost)
                                .amount(enteredSuppliedAmount)
                                .build()
                ))
                .build();
    }

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_CREATED_PRODUCT_TITLE = "Поставка оформлена";
        private static final String INFO_ALERT_CREATED_PRODUCT_HEADER = "Поставка была успешно оформлена!";
        private static final String INFO_ALERT_CREATED_PRODUCT_CONTENT = "Товары доступны для покупки";
    }
}
