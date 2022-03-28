package com.s0qva.application.controller;

import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.session.UserSession;
import lombok.Data;

@Data
public abstract class OrderController {
    private final OrderService orderService;
    private final FxmlPageLoader fxmlPageLoader;
    private final UserSession userSession;
    private final Cart cart;

    public OrderController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        this.orderService = orderService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.userSession = UserSession.getInstance();
        this.cart = Cart.getInstance();
    }
}
