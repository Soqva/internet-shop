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
    private final ProductIdToProductMapper productIdToProductMapper;
    private final UserIdToUserMapper userIdToUserMapper;

    @Autowired
    public OrderCreationToOrderMapper(ProductIdToProductMapper productIdToProductMapper,
                                      UserIdToUserMapper userIdToUserMapper) {
        this.productIdToProductMapper = productIdToProductMapper;
        this.userIdToUserMapper = userIdToUserMapper;
    }

    @Override
    public Order map(OrderCreationDto orderCreationDto) {
        List<Product> products = orderCreationDto.getProducts()
                .stream()
                .map(productIdToProductMapper::map)
                .collect(Collectors.toList());

        return Order.builder()
                .user(userIdToUserMapper.map(orderCreationDto.getUserId()))
                .orderDate(orderCreationDto.getOrderDate())
                .status(orderCreationDto.getStatus())
                .products(products)
                .build();
    }
}
