package com.s0qva.application.service;

import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.dictionary.DictionaryOrderStatus;
import com.s0qva.application.repository.DictionaryOrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryOrderStatusService {
    private final DictionaryOrderStatusRepository dictionaryOrderStatusRepository;

    public List<DictionaryOrderStatusDto> getAll() {
        return dictionaryOrderStatusRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public DictionaryOrderStatusDto getById(Long id) {
        return dictionaryOrderStatusRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    private DictionaryOrderStatusDto mapToDto(DictionaryOrderStatus dictionaryOrderStatus) {
        return DefaultMapper.mapToDto(dictionaryOrderStatus, DictionaryOrderStatusDto.class);
    }

    private DictionaryOrderStatus mapToEntity(DictionaryOrderStatusDto dictionaryOrderStatusDto) {
        return DefaultMapper.mapToEntity(dictionaryOrderStatusDto, DictionaryOrderStatus.class);
    }
}
