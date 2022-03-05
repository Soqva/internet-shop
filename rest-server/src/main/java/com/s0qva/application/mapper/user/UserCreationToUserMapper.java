package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.order.OrderCreationToOrderMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCreationToUserMapper implements Mapper<UserCreationDto, User> {
    private OrderCreationToOrderMapper mapper;

    @Override
    public User map(UserCreationDto userCreationDto) {
        List<Order> orders = userCreationDto.getOrders()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        User user = User.builder()
                .name(userCreationDto.getName())
                .build();

        orders.forEach(user::addOrder);

        return user;
    }

    @Autowired
    public void setMapper(OrderCreationToOrderMapper mapper) {
        this.mapper = mapper;
    }
}
