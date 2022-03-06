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
    private OrderToReadingMapper mapper;

    @Override
    public UserReadingDto map(User user) {
        List<OrderReadingDto> orders = user.getOrders()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return UserReadingDto.builder()
                .id(user.getId())
                .name(user.getName())
                .orders(orders)
                .build();
    }

    @Autowired
    public void setMapper(OrderToReadingMapper mapper) {
        this.mapper = mapper;
    }
}
