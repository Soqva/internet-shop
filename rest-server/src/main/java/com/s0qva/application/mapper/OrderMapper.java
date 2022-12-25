package com.s0qva.application.mapper;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.UserDto;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.UserOrder;
import lombok.experimental.UtilityClass;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

        mapper.addMappings(new PropertyMap<Order, OrderDto>() {
            @Override
            protected void configure() {
                var entityToDtoCommodityConverter = getEntityToDtoCommodityConverter();
                var entityToDtoUserConverter = getEntityToDtoUserConverter();

                using(entityToDtoCommodityConverter).map(source).setOrderedCommodities(null);
                using(entityToDtoUserConverter).map(source).setUser(null);
            }
        });
        mapper.addMappings(new PropertyMap<OrderDto, Order>() {
            @Override
            protected void configure() {
                var dtoToEntityOrderCostConverter = getDtoToEntityOrderCostConverter();

                skip().setOrderCommodities(null);
                using(dtoToEntityOrderCostConverter).map(source).setOrderCost(null);
            }
        });
        return mapper;
    }

    private Converter<Order, List<CommodityDto>> getEntityToDtoCommodityConverter() {
        return context -> context.getSource().getOrderCommodities()
                .stream()
                .map(orderCommodity -> {
                    var commodity = orderCommodity.getCommodity();
                    var commodityDto = mapInternal(commodity, CommodityDto.class);

                    commodityDto.setAmount(null);
                    return commodityDto;
                })
                .collect(toList());
    }

    private Converter<Order, UserDto> getEntityToDtoUserConverter() {
        return context -> context.getSource().getUserOrders()
                .stream()
                .map(UserOrder::getUser)
                .map(UserMapper::mapToDto)
                .peek(currentUserDto -> currentUserDto.setPassword(null))
                .findFirst()
                .orElse(null);
    }

    private Converter<OrderDto, Double> getDtoToEntityOrderCostConverter() {
        return context -> context.getSource().getOrderedCommodities()
                .stream()
                .filter(currentCommodityDto -> !ObjectUtils.isEmpty(currentCommodityDto.getAmount()))
                .reduce(0.0, (subOrderCost, secondCommodity) -> subOrderCost
                        + secondCommodity.getCost() * secondCommodity.getAmount(), Double::sum);
    }
}
