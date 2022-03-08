package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.mapper.product.detail.ProductDetailsCreationToProductDetailsMapper;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductCreationToProductMapper implements Mapper<ProductCreationDto, Product> {
    private ProductDetailsCreationToProductDetailsMapper mapper;

    @Override
    public Product map(ProductCreationDto productCreationDto) {
        Product product = Product.builder()
                .name(productCreationDto.getName())
                .price(productCreationDto.getPrice())
                .build();
        product.addDetails(mapper.map(productCreationDto.getDetails()));

        return product;
    }

    @Autowired
    public void setMapper(ProductDetailsCreationToProductDetailsMapper mapper) {
        this.mapper = mapper;
    }
}
