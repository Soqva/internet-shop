package com.s0qva.application.service;

import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.OrderCommodity;
import com.s0qva.application.repository.OrderCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCommodityService {
    private final OrderCommodityRepository orderCommodityRepository;

    @Transactional
    public OrderCommodity create(Long orderId, Long commodityId, Integer amountOfBoughtCommodity) {
        var orderCommodity = OrderCommodity.builder()
                .order(Order.builder().id(orderId).build())
                .commodity(Commodity.builder().id(commodityId).build())
                .amountOfBoughtCommodities(amountOfBoughtCommodity)
                .build();
        return orderCommodityRepository.save(orderCommodity);
    }
}
