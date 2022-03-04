package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private Mapper<Order, OrderReadingDto> orderReadDtoMapper;

    public List<OrderReadingDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderReadDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    @Qualifier("orderReadingMapper")
    public void setOrderReadDtoMapper(Mapper<Order, OrderReadingDto> orderReadDtoMapper) {
        this.orderReadDtoMapper = orderReadDtoMapper;
    }
}
