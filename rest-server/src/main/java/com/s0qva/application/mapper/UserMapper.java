package com.s0qva.application.mapper;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.UserDto;
import com.s0qva.application.dto.dictionary.DictionaryRoleDto;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.User;
import com.s0qva.application.model.UserRole;
import lombok.experimental.UtilityClass;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class UserMapper {
    private final ModelMapper MAPPER = prepareMapper();

    public User mapToEntity(UserDto dto) {
        return mapInternal(dto, User.class);
    }

    public UserDto mapToDto(User entity) {
        return mapInternal(entity, UserDto.class);
    }

    private <T> T mapInternal(Object sourceObject, Class<T> destinationClass) {
        return MAPPER.map(sourceObject, destinationClass);
    }

    private ModelMapper prepareMapper() {
        var mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                var entityToDtoRoleConverter = getEntityToDtoRoleConverter();

                using(entityToDtoRoleConverter).map(source).setRoles(null);
            }
        });
        return mapper;
    }

    private Converter<User, List<DictionaryRoleDto>> getEntityToDtoRoleConverter() {
        return context -> context.getSource().getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .map(role -> mapInternal(role, DictionaryRoleDto.class))
                .collect(toList());
    }
}
