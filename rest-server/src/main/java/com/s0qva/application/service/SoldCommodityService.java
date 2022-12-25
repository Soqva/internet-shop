package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.SoldCommodityDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.SoldCommodity;
import com.s0qva.application.repository.SoldCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoldCommodityService {
    private final SoldCommodityRepository soldCommodityRepository;

    public SoldCommodityDto create(Commodity commodity, int amountOfSoldCommodities) {
        var soldCommodity = SoldCommodity.builder()
                .commodity(commodity)
                .amountOfSoldCommodities(amountOfSoldCommodities)
                .soldDate(System.currentTimeMillis())
                .build();
        var createdSoldCommodity = soldCommodityRepository.save(soldCommodity);
        return DefaultMapper.mapToDto(createdSoldCommodity, SoldCommodityDto.class);
    }
}
