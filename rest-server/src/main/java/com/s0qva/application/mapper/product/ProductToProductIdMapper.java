package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductIdMapper implements Mapper<Product, ProductIdDto> {

    @Override
    public ProductIdDto map(Product product) {
        return ProductIdDto.builder()
                .id(product.getId())
                .build();
    }
}
