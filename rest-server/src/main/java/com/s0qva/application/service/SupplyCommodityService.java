package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.Supply;
import com.s0qva.application.model.SupplyCommodity;
import com.s0qva.application.repository.SupplyCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplyCommodityService {
    private final SupplyCommodityRepository supplyCommodityRepository;

    public SupplyCommodity getBySupplyIdAndCommodityId(Long supplyId, Long commodityId) {
        return supplyCommodityRepository.findBySupplyIdAndCommodityId(supplyId, commodityId).orElse(null);
    }

    @Transactional
    public SupplyCommodity createOrUpdate(SupplyDto supplyDto, CommodityDto commodityDto, Integer amountOfSuppliedCommodities) {
        return createOrUpdate(supplyDto.getId(), commodityDto.getId(), amountOfSuppliedCommodities);
    }

    @Transactional
    public SupplyCommodity createOrUpdate(Long supplyId, Long commodityId, Integer amountOfSuppliedCommodities) {
        var supplyCommodity = mapToEntity(supplyId, commodityId, amountOfSuppliedCommodities);

        return createOrUpdate(supplyCommodity);
    }

    @Transactional
    public SupplyCommodity createOrUpdate(SupplyCommodity supplyCommodity) {
        var existingSupplyCommodity = getBySupplyIdAndCommodityId(
                supplyCommodity.getSupply().getId(),
                supplyCommodity.getCommodity().getId()
        );
        if (!ObjectUtils.isEmpty(existingSupplyCommodity)) {
            supplyCommodity.setId(existingSupplyCommodity.getId());
        }
        return supplyCommodityRepository.save(supplyCommodity);
    }

    private SupplyCommodity mapToEntity(Long supplyId, Long commodityId, Integer amountOfSuppliedCommodities) {
        return SupplyCommodity.builder()
                .supply(Supply.builder()
                        .id(supplyId)
                        .build())
                .commodity(Commodity.builder()
                        .id(commodityId)
                        .build())
                .amountOfSuppliedCommodities(amountOfSuppliedCommodities)
                .build();
    }
}
