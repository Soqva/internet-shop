package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.detail.ProductDetailsCreationToProductDetailsMapper;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductCreationToProductMapper implements Mapper<ProductCreationDto, Product> {
    private final ProductDetailsCreationToProductDetailsMapper productDetailsCreationToProductDetailsMapper;

    @Autowired
    public ProductCreationToProductMapper(ProductDetailsCreationToProductDetailsMapper productDetailsCreationToProductDetailsMapper) {
        this.productDetailsCreationToProductDetailsMapper = productDetailsCreationToProductDetailsMapper;
    }

    @Override
    public Product map(ProductCreationDto productCreationDto) {
        Product product = Product.builder()
                .name(productCreationDto.getName())
                .price(productCreationDto.getPrice())
                .build();
        product.addDetails(productDetailsCreationToProductDetailsMapper.map(productCreationDto.getDetails()));

        return product;
    }
}
