package com.s0qva.application.service;

import com.s0qva.application.dto.dictionary.DictionaryCountryDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.dictionary.DictionaryCountry;
import com.s0qva.application.repository.DictionaryCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryCountryService {
    private final DictionaryCountryRepository dictionaryCountryRepository;

    public List<DictionaryCountryDto> getAll() {
        return dictionaryCountryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public DictionaryCountryDto getById(Long id) {
        return dictionaryCountryRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    private DictionaryCountryDto mapToDto(DictionaryCountry dictionaryCountry) {
        return DefaultMapper.mapToDto(dictionaryCountry, DictionaryCountryDto.class);
    }

    private DictionaryCountry mapToEntity(DictionaryCountryDto dictionaryCountryDto) {
        return DefaultMapper.mapToEntity(dictionaryCountryDto, DictionaryCountry.class);
    }
}
