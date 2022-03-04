package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderReadingMapper implements Mapper<Order, OrderReadingDto> {

    @Override
    public OrderReadingDto map(Order order) {
        return OrderReadingDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .customerId(order.getUser().getId())
                .products(order.getProducts())
                .build();
    }
}
