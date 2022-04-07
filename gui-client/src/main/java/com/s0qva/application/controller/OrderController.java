package com.s0qva.application.controller;

import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.model.Cart;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.session.UserSession;
import lombok.Data;

@Data
public abstract class OrderController {
    protected final OrderService orderService;
    protected final FxmlPageLoader fxmlPageLoader;
    protected final UserSession userSession;
    protected final Cart cart;

    public OrderController(OrderService orderService, FxmlPageLoader fxmlPageLoader) {
        this.orderService = orderService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.userSession = UserSession.getInstance();
        this.cart = Cart.getInstance();
    }
}
