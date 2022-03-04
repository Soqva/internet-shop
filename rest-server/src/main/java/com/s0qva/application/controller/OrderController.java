package com.s0qva.application.controller;

import com.s0qva.application.dto.order.OrderReadDto;
import com.s0qva.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderReadDto>> getAll() {
        List<OrderReadDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
