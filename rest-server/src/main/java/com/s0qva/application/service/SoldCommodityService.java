package com.s0qva.application.service;

import com.s0qva.application.dto.SoldCommodityDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.model.SoldCommodity;
import com.s0qva.application.repository.SoldCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoldCommodityService {
    private final SoldCommodityRepository soldCommodityRepository;

    public List<SoldCommodityDto> getAll() {
        return soldCommodityRepository.findAll().stream()
                .map(currentSoldCommodity -> DefaultMapper.mapToDto(currentSoldCommodity, SoldCommodityDto.class))
                .collect(toList());
    }

    public List<SoldCommodityDto> getAllDuringCurrentDay() {
        var duration = Duration.ofMillis(System.currentTimeMillis());
        var leftBound = duration.minusDays(1L).toMillis();
        var rightBound = duration.plusDays(1L).toMillis();

        return soldCommodityRepository.findAllDuringTime(leftBound, rightBound).stream()
                .map(currentSoldCommodity -> DefaultMapper.mapToDto(currentSoldCommodity, SoldCommodityDto.class))
                .collect(toList());
    }

    @Transactional
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
