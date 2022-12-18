package com.s0qva.application.mapper;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.util.ObjectUtils;

@UtilityClass
public class DefaultMapper {
    private final ModelMapper MAPPER = new ModelMapper();

    public <T> T mapToEntity(Object dto, Class<T> entity, PropertyMap<?, ?> additionalProperties) {
        return mapInternal(dto, entity, additionalProperties);
    }

    public <T> T mapToEntity(Object dto, Class<T> entity) {
        return mapToEntity(dto, entity, null);
    }

    public <T> T mapToDto(Object entity, Class<T> dto, PropertyMap<?, ?> additionalProperties) {
        return mapInternal(entity, dto, additionalProperties);
    }

    public <T> T mapToDto(Object entity, Class<T> dto) {
        return mapToDto(entity, dto, null);
    }

    private <T> T mapInternal(Object sourceObject, Class<T> destinationClass, PropertyMap<?, ?> additionalProperties) {
        if (!ObjectUtils.isEmpty(additionalProperties)) {
            MAPPER.addMappings(additionalProperties);
        }
        return MAPPER.map(sourceObject, destinationClass);
    }
}
