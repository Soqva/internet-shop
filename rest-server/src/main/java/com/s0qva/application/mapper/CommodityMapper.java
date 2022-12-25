package com.s0qva.application.mapper;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.model.Commodity;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@UtilityClass
public class CommodityMapper {
    private final ModelMapper MAPPER = prepareMapper();

    public Commodity mapToEntity(CommodityDto dto) {
        return mapInternal(dto, Commodity.class);
    }

    public CommodityDto mapToDto(Commodity entity) {
        return mapInternal(entity, CommodityDto.class);
    }

    private <T> T mapInternal(Object sourceObject, Class<T> destinationClass) {
        return MAPPER.map(sourceObject, destinationClass);
    }

    private ModelMapper prepareMapper() {
        var mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<Commodity, CommodityDto>() {
            @Override
            protected void configure() {
                map().setAmount(null);
            }
        });
        return mapper;
    }
}
