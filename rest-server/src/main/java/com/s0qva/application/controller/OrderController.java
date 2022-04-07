package com.s0qva.application.controller;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderReadingDto>> getAll() {
        log.info("Received a request for getting all orders.");
        List<OrderReadingDto> orders = orderService.getAllOrders();

        log.info("Sending a list of orders: {}. The response status is OK", orders);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderReadingDto> getOne(@PathVariable Long id) {
        log.info("Received a request for getting an order by its id = {}.", id);
        OrderReadingDto order = orderService.getOrder(id);

        log.info("Sending an order: {}. The response status is OK", order);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> save(@Valid @RequestBody OrderCreationDto order) {
        log.info("Received a request for saving an order. OrderCreationDto: {}.", order);
        OrderIdDto savedOrderId = orderService.saveOrder(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrderId.getId())
                .toUri();

        log.info("Nothing to send. OrderIdDto savedOrderId {}. URI location: {}. The response status is CREATED",
                savedOrderId,
                location);
        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderReadingDto> update(@PathVariable Long id, @Valid @RequestBody OrderCreationDto order) {
        log.info("Received a request for updating an order. Order's Id: {}. OrderCreationDto: {}.", id, order);
        OrderReadingDto updatedOrder = orderService.updateOrder(id, order);

        log.info("Sending an order: {}. The response status is OK", updatedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received a request for deleting an order. Order's Id: {}.", id);
        orderService.deleteOrder(id);

        log.info("Nothing to send. The response status is OK");
        return ResponseEntity.ok()
                .build();
    }
}
