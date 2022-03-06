package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.ProductIdToProductMapper;
import com.s0qva.application.mapper.user.UserIdToUserMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreationToOrderMapper implements Mapper<OrderCreationDto, Order> {
    private ProductIdToProductMapper mapper;
    private UserIdToUserMapper userIdToUserMapper;

    @Override
    public Order map(OrderCreationDto orderCreationDto) {
        List<Product> products = orderCreationDto.getProducts()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return Order.builder()
                .user(userIdToUserMapper.map(orderCreationDto.getUserId()))
                .orderDate(orderCreationDto.getOrderDate())
                .products(products)
                .build();
    }

    @Autowired
    public void setMapper(ProductIdToProductMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setUserIdToUserMapper(UserIdToUserMapper userIdToUserMapper) {
        this.userIdToUserMapper = userIdToUserMapper;
    }
}
