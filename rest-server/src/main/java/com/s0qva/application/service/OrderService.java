package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.exception.NoSuchOrderException;
import com.s0qva.application.exception.model.enumeration.DefaultExceptionMessage;
import com.s0qva.application.mapper.order.GeneralOrderMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final GeneralOrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, GeneralOrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderReadingDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(orderMapper::mapOrderToOrderReadingDto)
                .collect(Collectors.toList());
    }

    public OrderReadingDto getOrder(Long id) {
        Optional<Order> maybeOrder = orderRepository.findById(id);

        return maybeOrder.map(orderMapper::mapOrderToOrderReadingDto)
                .orElseThrow(() ->
                        new NoSuchOrderException(DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + id));
    }

    public OrderIdDto saveOrder(OrderCreationDto orderCreationDto) {
        Order order = orderMapper.mapOrderCreationDtoToOrder(orderCreationDto);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderToOrderIdDto(savedOrder);
    }

    public OrderReadingDto updateOrder(Long id, OrderCreationDto orderCreationDto) {
        Optional<Order> maybeOldOrder = orderRepository.findById(id);
        Order oldOrder = maybeOldOrder.orElseThrow(() ->
                new NoSuchOrderException(DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + id));
        Order newOrder = orderMapper.mapOrderCreationDtoToOrder(orderCreationDto);

        oldOrder.setStatus(newOrder.getStatus());

        Order updatedOrder = orderRepository.save(oldOrder);

        return orderMapper.mapOrderToOrderReadingDto(updatedOrder);

    }

    public void deleteOrder(Long id) {
        Optional<Order> maybeOrder = orderRepository.findById(id);
        Order order = maybeOrder.orElseThrow(() ->
                new NoSuchOrderException(DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + id));

        orderRepository.delete(order);
    }
}
