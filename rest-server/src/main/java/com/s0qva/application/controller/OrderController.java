package com.s0qva.application.controller;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderReadingDto>> getAll() {
        List<OrderReadingDto> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderReadingDto> getOne(@PathVariable Long id) {
        OrderReadingDto order = orderService.getOrder(id);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> save(@Valid @RequestBody OrderCreationDto order) {
        OrderIdDto savedOrderId = orderService.saveOrder(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrderId.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderReadingDto> update(@PathVariable Long id, @Valid @RequestBody OrderCreationDto order) {
        OrderReadingDto updatedOrder = orderService.updateOrder(id, order);

        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.ok()
                .build();
    }
}
