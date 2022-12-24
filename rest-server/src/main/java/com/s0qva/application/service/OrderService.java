package com.s0qva.application.service;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.mapper.OrderMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.OrderCommodity;
import com.s0qva.application.model.UserOrder;
import com.s0qva.application.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserOrderService userOrderService;
    private final OrderCommodityService orderCommodityService;
    private final OrderRepository orderRepository;

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public List<OrderDto> getAllByUserId(Long userId) {
        return userOrderService.getAllByUserId(userId).stream()
                .map(UserOrder::getOrder)
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional
    public OrderDto create(OrderDto orderDto) {
        var createdOrderDto = saveOrUpdateOrder(orderDto);
        var createdOrderId = createdOrderDto.getId();

        orderDto.getOrderedCommodities().forEach(orderedCommodity ->
                saveOrUpdateOrderCommodity(createdOrderId, orderedCommodity.getId(), orderedCommodity.getAmount())
        );
        saveOrUpdateUserOrder(orderDto.getUser().getId(), createdOrderId);
        return createdOrderDto;
    }

    @Transactional
    public OrderDto update(Long id, OrderDto orderDto) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    orderDto.setId(existingOrder.getId());
                    create(orderDto);
                    return orderDto;
                })
                .orElseThrow(() -> new RuntimeException("There is not order with id = " + id));
    }

    private OrderDto saveOrUpdateOrder(OrderDto orderDto) {
        var order = mapToEntity(orderDto);
        var createdOrder = orderRepository.save(order);

        return mapToDto(createdOrder);
    }

    private OrderCommodity saveOrUpdateOrderCommodity(Long orderId, Long commodityId, Integer orderedAmount) {
        return orderCommodityService.createOrUpdate(orderId, commodityId, orderedAmount);
    }

    private UserOrder saveOrUpdateUserOrder(Long userId, Long orderId) {
        return userOrderService.create(userId, orderId);
    }

    private OrderDto mapToDto(Order order) {
        return OrderMapper.mapToDto(order);
    }

    private Order mapToEntity(OrderDto orderDto) {
        return OrderMapper.mapToEntity(orderDto);
    }
}
