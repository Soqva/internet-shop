package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.ProductCreationToProductMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreationToOrderMapper implements Mapper<OrderCreationDto, Order> {
    private ProductCreationToProductMapper mapper;

    @Override
    public Order map(OrderCreationDto orderCreationDto) {
        List<Product> products = orderCreationDto.getProducts()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return Order.builder()
                .orderDate(orderCreationDto.getOrderDate())
                .products(products)
                .build();
    }

    @Autowired
    public void setProductMapper(ProductCreationToProductMapper mapper) {
        this.mapper = mapper;
    }
}
