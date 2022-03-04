package com.s0qva.application.mapper;

import com.s0qva.application.dto.order.OrderReadDto;
import com.s0qva.application.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    @Override
    public OrderReadDto map(Order order) {
        return OrderReadDto.builder()
                .orderDate(order.getOrderDate())
                .customerId(order.getUser().getId())
                .products(order.getProducts())
                .build();
    }
}
