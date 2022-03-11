package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductIdToProductMapper implements Mapper<ProductIdDto, Product> {

    @Override
    public Product map(ProductIdDto productIdDto) {
        return Product.builder()
                .id(productIdDto.getId())
                .build();
    }
}
