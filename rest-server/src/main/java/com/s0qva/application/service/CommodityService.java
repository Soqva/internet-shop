package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.Commodity;
import com.s0qva.application.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommodityService {
    private final CommodityRepository commodityRepository;

    public List<CommodityDto> getAll() {
        return commodityRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public CommodityDto getById(Long id) {
        return commodityRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Transactional
    public CommodityDto createOrUpdate(CommodityDto commodityDto) {
        var commodity = mapToEntity(commodityDto);

        return Optional.of(commodityRepository.save(commodity))
                .map(this::mapToDto)
                .orElse(null);
    }

    private CommodityDto mapToDto(Commodity commodity) {
        return DefaultMapper.mapToDto(commodity, CommodityDto.class);
    }

    private Commodity mapToEntity(CommodityDto commodityDto) {
        return DefaultMapper.mapToEntity(commodityDto, Commodity.class);
    }
}
