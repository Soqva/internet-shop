package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderIdMapper implements Mapper<Order, OrderIdDto> {

    @Override
    public OrderIdDto map(Order order) {
        return OrderIdDto.builder()
                .id(order.getId())
                .build();
    }
}
