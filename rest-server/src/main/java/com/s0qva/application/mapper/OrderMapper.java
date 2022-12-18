package com.s0qva.application.mapper;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.model.Order;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@UtilityClass
public class OrderMapper {
    private final ModelMapper MAPPER = prepareMapper();

    public Order mapToEntity(OrderDto dto) {
        return mapInternal(dto, Order.class);
    }

    public OrderDto mapToDto(Order entity) {
        return mapInternal(entity, OrderDto.class);
    }

    private <T> T mapInternal(Object sourceObject, Class<T> destinationClass) {
        return MAPPER.map(sourceObject, destinationClass);
    }

    private ModelMapper prepareMapper() {
        var mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<OrderDto, Order>() {
            @Override
            protected void configure() {
                skip().setOrderCommodities(null);
            }
        });
        return mapper;
    }
}
