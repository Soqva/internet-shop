package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommodityService {
    private final CommodityRepository commodityRepository;

    public List<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    public Commodity getById(Long id) {
        return commodityRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Long createOrUpdate(CommodityDto commodityDto) {
        var commodity = Commodity.builder()
                .name(commodityDto.getName())
                .cost(commodityDto.getCost())
                .availableAmount(commodityDto.getAvailableAmount())
                .description(commodityDto.getDescription())
                .producingCountry(commodityDto.getProducingCountry())
                .build();
        if (!ObjectUtils.isEmpty(commodityDto.getId())) {
            commodity.setId(commodityDto.getId());
        }
        return commodityRepository.save(commodity).getId();
    }
}
