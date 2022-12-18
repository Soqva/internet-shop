package com.s0qva.application.service;

import com.s0qva.application.dto.dictionary.DictionaryRoleDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.dictionary.DictionaryRole;
import com.s0qva.application.model.enumeration.Role;
import com.s0qva.application.repository.DictionaryRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.s0qva.application.model.enumeration.Role.USER;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryRoleService {
    public static final Role DEFAULT_ROLE = USER;
    private final DictionaryRoleRepository dictionaryRoleRepository;

    public List<DictionaryRoleDto> getAll() {
        return dictionaryRoleRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public DictionaryRoleDto getById(Long id) {
        return dictionaryRoleRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public DictionaryRoleDto getDefaultRole() {
        return dictionaryRoleRepository.findByName(DEFAULT_ROLE.getName())
                .map(this::mapToDto)
                .orElse(null);
    }

    private DictionaryRoleDto mapToDto(DictionaryRole dictionaryRole) {
        return DefaultMapper.mapToDto(dictionaryRole, DictionaryRoleDto.class);
    }

    private DictionaryRole mapToEntity(DictionaryRoleDto dictionaryRoleDto) {
        return DefaultMapper.mapToEntity(dictionaryRoleDto, DictionaryRole.class);
    }
}
