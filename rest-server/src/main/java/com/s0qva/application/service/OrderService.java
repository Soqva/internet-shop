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

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserOrderService userOrderService;
    private final OrderCommodityService orderCommodityService;
    private final OrderRepository orderRepository;

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

    private OrderDto saveOrUpdateOrder(OrderDto orderDto) {
        var orderCost = determineOrderCost(orderDto);
        var order = mapToEntity(orderDto);

        order.setOrderCost(orderCost);

        var createdOrder = orderRepository.save(order);

        return mapToDto(createdOrder);
    }

    private OrderCommodity saveOrUpdateOrderCommodity(Long orderId, Long commodityId, Integer orderedAmount) {
        return orderCommodityService.createOrUpdate(orderId, commodityId, orderedAmount);
    }

    private UserOrder saveOrUpdateUserOrder(Long userId, Long orderId) {
        return userOrderService.create(userId, orderId);
    }

    private Double determineOrderCost(OrderDto orderDto) {
        return orderDto.getOrderedCommodities().stream()
                .reduce(0.0, (subOrderCost, secondCommodity) -> subOrderCost
                        + secondCommodity.getCost() * secondCommodity.getAmount(), Double::sum);
    }

    private OrderDto mapToDto(Order order) {
        return OrderMapper.mapToDto(order);
    }

    private Order mapToEntity(OrderDto orderDto) {
        return OrderMapper.mapToEntity(orderDto);
    }
}
