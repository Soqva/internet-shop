package com.s0qva.application.service;

import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.model.Supply;
import com.s0qva.application.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplyService {
    private final CommodityService commodityService;
    private final SupplyCommodityService supplyCommodityService;
    private final SupplyRepository supplyRepository;

    @Transactional
    public Long create(SupplyDto supplyDto) {
        var supply = Supply.builder()
                .supplier(supplyDto.getSupplier())
                .receivingDate(supplyDto.getReceivingDate())
                .build();
        var createdSupplyId = supplyRepository.save(supply).getId();

        for (var commodityWithSuppliedAmount : supplyDto.getSuppliedCommodities().entrySet()) {
            if (ObjectUtils.isEmpty(commodityWithSuppliedAmount.getValue().getId())) {
                commodityWithSuppliedAmount.getValue().setAvailableAmount(commodityWithSuppliedAmount.getKey());

                var createdCommodityId = commodityService.createOrUpdate(commodityWithSuppliedAmount.getValue());

                commodityWithSuppliedAmount.getValue().setId(createdCommodityId);
            } else {
                var commodity = commodityService.getById(commodityWithSuppliedAmount.getValue().getId());
                var updatedAvailableAmount = commodityWithSuppliedAmount.getKey() + commodity.getAvailableAmount();

                commodityWithSuppliedAmount.getValue().setAvailableAmount(updatedAvailableAmount);
                commodityService.createOrUpdate(commodityWithSuppliedAmount.getValue());
            }
            supplyCommodityService.create(
                    createdSupplyId,
                    commodityWithSuppliedAmount.getValue().getId(),
                    commodityWithSuppliedAmount.getKey()
            );
        }
        return createdSupplyId;
    }
}
