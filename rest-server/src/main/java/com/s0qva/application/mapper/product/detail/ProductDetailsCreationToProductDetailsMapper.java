package com.s0qva.application.mapper.product.detail;

import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.ProductDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsCreationToProductDetailsMapper implements Mapper<ProductDetailsCreationDto, ProductDetails> {

    @Override
    public ProductDetails map(ProductDetailsCreationDto productDetailsCreationDto) {
        return ProductDetails.builder()
                .description(productDetailsCreationDto.getDescription())
                .madeIn(productDetailsCreationDto.getMadeIn())
                .build();
    }
}
