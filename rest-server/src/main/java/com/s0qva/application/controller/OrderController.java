package com.s0qva.application.controller;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.util.ServletUriUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        var orders = orderService.getAll();

        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDto orderDto) {
        var createdOrderId = orderService.create(orderDto).getId();
        var location = ServletUriUtil.getUriFromCurrentRequest("/{id}", createdOrderId);

        return ResponseEntity.created(location).build();
    }
}
