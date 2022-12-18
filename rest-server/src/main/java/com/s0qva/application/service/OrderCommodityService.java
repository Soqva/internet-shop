package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.OrderCommodity;
import com.s0qva.application.repository.OrderCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCommodityService {
    private final OrderCommodityRepository orderCommodityRepository;

    public OrderCommodity getByOrderIdAndCommodityId(Long orderId, Long commodityId) {
        return orderCommodityRepository.findByOrderIdAndCommodityId(orderId, commodityId).orElse(null);
    }

    @Transactional
    public OrderCommodity createOrUpdate(OrderDto orderDto, CommodityDto commodityDto, Integer amountOfBoughtCommodity) {
        return createOrUpdate(orderDto.getId(), commodityDto.getId(), amountOfBoughtCommodity);
    }

    @Transactional
    public OrderCommodity createOrUpdate(Long orderId, Long commodityId, Integer amountOfBoughtCommodity) {
        var orderCommodity = mapToEntity(orderId, commodityId, amountOfBoughtCommodity);

        return createOrUpdate(orderCommodity);
    }

    @Transactional
    public OrderCommodity createOrUpdate(OrderCommodity orderCommodity) {
        var existingOrderCommodity = getByOrderIdAndCommodityId(
                orderCommodity.getOrder().getId(),
                orderCommodity.getCommodity().getId()
        );
        if (!ObjectUtils.isEmpty(existingOrderCommodity)) {
            orderCommodity.setId(existingOrderCommodity.getId());
        }
        return orderCommodityRepository.save(orderCommodity);
    }

    private OrderCommodity mapToEntity(Long orderId, Long commodityId, Integer amountOfBoughtCommodity) {
        return OrderCommodity.builder()
                .order(Order.builder()
                        .id(orderId)
                        .build())
                .commodity(Commodity.builder()
                        .id(commodityId)
                        .build())
                .amountOfBoughtCommodities(amountOfBoughtCommodity)
                .build();
    }
}
