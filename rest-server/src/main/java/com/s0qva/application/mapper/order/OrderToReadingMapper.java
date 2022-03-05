package com.s0qva.application.mapper.order;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.ProductToReadingMapper;
import com.s0qva.application.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderToReadingMapper implements Mapper<Order, OrderReadingDto> {
    private ProductToReadingMapper mapper;

    @Override
    public OrderReadingDto map(Order order) {
        List<ProductReadingDto> orders = order.getProducts()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return OrderReadingDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .userId(order.getUser().getId())
                .products(orders)
                .build();
    }

    @Autowired
    public void setMapper(ProductToReadingMapper mapper) {
        this.mapper = mapper;
    }
}
