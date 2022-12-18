package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.dto.dictionary.DictionarySupplierDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.Supply;
import com.s0qva.application.model.SupplyCommodity;
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
    private final DictionarySupplierService dictionarySupplierService;
    private final SupplyRepository supplyRepository;

    @Transactional
    public SupplyDto create(SupplyDto supplyDto) {
        var createdSupplyDto = saveOrUpdateSupply(supplyDto);

        supplyDto.getSuppliedCommodities().forEach(suppliedCommodity -> {
            var existingCommodity = saveOrUpdateCommodity(suppliedCommodity);

            saveOrUpdateSupplyCommodity(createdSupplyDto.getId(), existingCommodity.getId(), suppliedCommodity.getAmount());
        });
        return createdSupplyDto;
    }

    private SupplyDto saveOrUpdateSupply(SupplyDto supplyDto) {
        var supplierDto = supplyDto.getSupplier();

        if (ObjectUtils.isEmpty(supplierDto.getId())) {
            var createdSupplierDto = saveOrUpdateSupplier(supplierDto);

            supplyDto.setSupplier(createdSupplierDto);
        }
        var supply = mapToEntity(supplyDto);
        var createdSupply = supplyRepository.save(supply);

        return mapToDto(createdSupply);
    }

    private DictionarySupplierDto saveOrUpdateSupplier(DictionarySupplierDto supplierDto) {
        return dictionarySupplierService.createOrUpdate(supplierDto);
    }

    private CommodityDto saveOrUpdateCommodity(CommodityDto commodityDto) {
        if (ObjectUtils.isEmpty(commodityDto.getId())) {
            setInitialAvailableAmount(commodityDto);
            return commodityService.createOrUpdate(commodityDto);
        }
        var existingCommodity = commodityService.getById(commodityDto.getId());

        updateAvailableAmount(existingCommodity, commodityDto);
        return commodityService.createOrUpdate(existingCommodity);
    }

    private SupplyCommodity saveOrUpdateSupplyCommodity(Long supplyId, Long commodityId, Integer suppliedAmount) {
        return supplyCommodityService.createOrUpdate(supplyId, commodityId, suppliedAmount);
    }

    private void setInitialAvailableAmount(CommodityDto suppliedCommodity) {
        suppliedCommodity.setAvailableAmount(suppliedCommodity.getAmount());
    }

    private void updateAvailableAmount(CommodityDto existingCommodity, CommodityDto suppliedCommodity) {
        var updatedAvailableAmount = existingCommodity.getAvailableAmount() + suppliedCommodity.getAmount();

        existingCommodity.setAvailableAmount(updatedAvailableAmount);
    }

    private SupplyDto mapToDto(Supply supply) {
        return DefaultMapper.mapToDto(supply, SupplyDto.class);
    }

    private Supply mapToEntity(SupplyDto supplyDto) {
        return DefaultMapper.mapToEntity(supplyDto, Supply.class);
    }
}
