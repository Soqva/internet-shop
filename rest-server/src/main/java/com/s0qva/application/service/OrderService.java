package com.s0qva.application.service;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.mapper.OrderMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.OrderCommodity;
import com.s0qva.application.model.UserOrder;
import com.s0qva.application.model.dictionary.DictionaryOrderStatus;
import com.s0qva.application.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.s0qva.application.model.enumeration.OrderStatus.ACCEPTED;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserOrderService userOrderService;
    private final OrderCommodityService orderCommodityService;
    private final SoldCommodityService soldCommodityService;
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
        var existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is not order with id = " + id));
        var orderStatus = orderDto.getOrderStatus().getName();

        existingOrder.setOrderStatus(DefaultMapper.mapToEntity(orderDto.getOrderStatus(), DictionaryOrderStatus.class));

        if (!ACCEPTED.getName().equals(orderStatus)) {
            return saveOrUpdateOrder(existingOrder);
        }
        var updatedOrder = saveOrUpdateOrder(existingOrder);

        existingOrder.getOrderCommodities().forEach(currentOrderCommodity -> {
            var currentOrderedCommodity = currentOrderCommodity.getCommodity();
            var orderedCommodityAmount = currentOrderCommodity.getAmountOfBoughtCommodities();
            var oldCommodityAmount = currentOrderedCommodity.getAvailableAmount();

            currentOrderedCommodity.setAvailableAmount(oldCommodityAmount - orderedCommodityAmount);
            soldCommodityService.create(currentOrderedCommodity, orderedCommodityAmount);
            saveOrUpdateOrderCommodity(
                    existingOrder.getId(),
                    currentOrderedCommodity.getId(),
                    orderedCommodityAmount
            );
        });
        return updatedOrder;
    }

    private OrderDto saveOrUpdateOrder(Order order) {
        var createdOrder = orderRepository.save(order);

        return mapToDto(createdOrder);
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
