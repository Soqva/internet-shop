package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderIdToOrderMapper implements Mapper<OrderIdDto, Order> {

    @Override
    public Order map(OrderIdDto orderIdDto) {
        return Order.builder()
                .id(orderIdDto.getId())
                .build();
    }
}
