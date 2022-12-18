package com.s0qva.application.service;

import com.s0qva.application.dto.dictionary.DictionarySupplierDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.dictionary.DictionarySupplier;
import com.s0qva.application.repository.DictionarySupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionarySupplierService {
    private final DictionarySupplierRepository dictionarySupplierRepository;

    public List<DictionarySupplierDto> getAll() {
        return dictionarySupplierRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public DictionarySupplierDto getById(Long id) {
        return dictionarySupplierRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Transactional
    public DictionarySupplierDto createOrUpdate(DictionarySupplierDto dictionarySupplierDto) {
        var dictionarySupplier = mapToEntity(dictionarySupplierDto);

        return Optional.of(dictionarySupplierRepository.save(dictionarySupplier))
                .map(this::mapToDto)
                .orElse(null);
    }

    private DictionarySupplierDto mapToDto(DictionarySupplier dictionarySupplier) {
        return DefaultMapper.mapToDto(dictionarySupplier, DictionarySupplierDto.class);
    }

    private DictionarySupplier mapToEntity(DictionarySupplierDto dictionarySupplierDto) {
        return DefaultMapper.mapToEntity(dictionarySupplierDto, DictionarySupplier.class);
    }
}
