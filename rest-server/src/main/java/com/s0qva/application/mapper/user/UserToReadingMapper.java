package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.order.OrderToReadingMapper;
import com.s0qva.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserToReadingMapper implements Mapper<User, UserReadingDto> {
    private final OrderToReadingMapper orderToReadingMapper;

    @Autowired
    public UserToReadingMapper(OrderToReadingMapper orderToReadingMapper) {
        this.orderToReadingMapper = orderToReadingMapper;
    }

    @Override
    public UserReadingDto map(User user) {
        List<OrderReadingDto> orders = user.getOrders()
                .stream()
                .map(orderToReadingMapper::map)
                .collect(Collectors.toList());

        return UserReadingDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .banned(user.isBanned())
                .orders(orders)
                .build();
    }
}
