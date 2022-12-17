package com.s0qva.application.service;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.model.Order;
import com.s0qva.application.repository.CommodityRepository;
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
    public Long create(OrderDto orderDto) {
        var orderedCommodities = orderDto.getOrderedCommodities();
        var orderCost = 0.0;

        for (var commodityWithBoughtAmount : orderedCommodities.entrySet()) {
            orderCost += commodityWithBoughtAmount.getValue().getCost() * commodityWithBoughtAmount.getKey();
        }
        var order = Order.builder()
                .id(orderDto.getId())
                .orderStatus(orderDto.getOrderStatus())
                .orderCost(orderCost)
                .build();
        var createdOrderId = orderRepository.save(order).getId();

        for (var commodityWithBoughtAmount : orderedCommodities.entrySet()) {
            orderCommodityService.create(
                    createdOrderId,
                    commodityWithBoughtAmount.getValue().getId(),
                    commodityWithBoughtAmount.getKey()
            );
        }
        userOrderService.create(orderDto.getUser().getId(), createdOrderId);
        return createdOrderId;
    }
}
