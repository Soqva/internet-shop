package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderIdDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.exception.NoSuchProductException;
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
    private OrderRepository orderRepository;
    private GeneralOrderMapper orderMapper;

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
                        new NoSuchProductException(DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + id));
    }

    public OrderIdDto saveOrder(OrderCreationDto orderCreationDto) {
        Order order = orderMapper.mapOrderCreationDtoToOrder(orderCreationDto);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.mapOrderToOrderIdDto(savedOrder);
    }

    public void deleteOrder(Long id) {
        Optional<Order> maybeOrder = orderRepository.findById(id);
        Order order = maybeOrder.orElseThrow(() ->
                new NoSuchProductException(DefaultExceptionMessage.NO_SUCH_ORDER_WITH_ID.getMessage() + id));

        orderRepository.delete(order);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderMapper(GeneralOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
}
