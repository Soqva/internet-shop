package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.detail.ProductDetailsToReadingMapper;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductToReadingMapper implements Mapper<Product, ProductReadingDto> {
    private final ProductDetailsToReadingMapper productDetailsToReadingMapper;

    @Autowired
    public ProductToReadingMapper(ProductDetailsToReadingMapper productDetailsToReadingMapper) {
        this.productDetailsToReadingMapper = productDetailsToReadingMapper;
    }

    @Override
    public ProductReadingDto map(Product product) {
        return ProductReadingDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .details(productDetailsToReadingMapper.map(product.getDetails()))
                .build();
    }
}
