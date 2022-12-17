package com.s0qva.application.service;

import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.Supply;
import com.s0qva.application.model.SupplyCommodity;
import com.s0qva.application.repository.SupplyCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplyCommodityService {
    private final SupplyCommodityRepository supplyCommodityRepository;

    @Transactional
    public SupplyCommodity create(Long supplyId, Long commodityId, Integer amountOfSuppliedCommodities) {
        var supplyCommodity = SupplyCommodity.builder()
                .supply(Supply.builder().id(supplyId).build())
                .commodity(Commodity.builder().id(commodityId).build())
                .amountOfSuppliedCommodities(amountOfSuppliedCommodities)
                .build();
        return supplyCommodityRepository.save(supplyCommodity);
    }
}
