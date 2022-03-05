package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.mapper.order.OrderToReadingMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderToReadingMapper orderToReadingMapper;

    public List<OrderReadingDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderToReadingMapper::map)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderReadingMapper(OrderToReadingMapper orderToReadingMapper) {
        this.orderToReadingMapper = orderToReadingMapper;
    }
}
