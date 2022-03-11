package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralOrderMapper {
    private final OrderCreationToOrderMapper orderCreationToOrderMapper;
    private final OrderToReadingMapper orderToReadingMapper;
    private final OrderIdToOrderMapper orderIdToOrderMapper;
    private final OrderToOrderIdMapper orderToOrderIdMapper;

    @Autowired
    public GeneralOrderMapper(OrderCreationToOrderMapper orderCreationToOrderMapper,
                              OrderToReadingMapper orderToReadingMapper,
                              OrderIdToOrderMapper orderIdToOrderMapper,
                              OrderToOrderIdMapper orderToOrderIdMapper) {
        this.orderCreationToOrderMapper = orderCreationToOrderMapper;
        this.orderToReadingMapper = orderToReadingMapper;
        this.orderIdToOrderMapper = orderIdToOrderMapper;
        this.orderToOrderIdMapper = orderToOrderIdMapper;
    }

    public Order mapOrderCreationDtoToOrder(OrderCreationDto orderCreationDto) {
        return orderCreationToOrderMapper.map(orderCreationDto);
    }

    public OrderReadingDto mapOrderToOrderReadingDto(Order order) {
        return orderToReadingMapper.map(order);
    }

    public Order mapOrderIdDtoToOrder(OrderIdDto orderIdDto) {
        return orderIdToOrderMapper.map(orderIdDto);
    }

    public OrderIdDto mapOrderToOrderIdDto(Order order) {
        return orderToOrderIdMapper.map(order);
    }
}
