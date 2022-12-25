package com.s0qva.application.controller;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.CommodityService;
import com.s0qva.application.service.SupplyService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.scene.control.ListView;
import lombok.Data;

@Data
public abstract class CommodityController {
    protected final CommodityService commodityService;
    protected final FxmlPageLoader fxmlPageLoader;
    protected final UserSession userSession;
    protected final Cart cart;

    public CommodityController(CommodityService commodityService, FxmlPageLoader fxmlPageLoader) {
        this.commodityService = commodityService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.userSession = UserSession.getInstance();
        this.cart = Cart.getInstance();
    }

    @SafeVarargs
    public final void addEventToShowProductDetails(ListView<CommodityDto>... listOfCommodities) {
        for (var currentList : listOfCommodities) {
            currentList.setOnMouseClicked(click -> {
                if (click.getClickCount() >= 2) {
                    var selectedCommodity = currentList.getSelectionModel().getSelectedItem();
                    var description = "Описание отсутствует";
                    var madeIn = "Неизвестно";

                    if (selectedCommodity.getDescription() != null) {
                        description = selectedCommodity.getDescription();
                    }

                    if (selectedCommodity.getProducingCountry() != null) {
                        madeIn = selectedCommodity.getProducingCountry().getName();
                    }
                    var content = "Описание: " + description + " \nПроизведено в: " + madeIn;

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
        private static final String INFO_ALERT_PRODUCT_DETAILS_TITLE = "Детали";
        private static final String INFO_ALERT_PRODUCT_DETAILS_HEADER = "Здесь представлены детали о товаре";
    }
}
