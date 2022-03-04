package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<ProductCreationDto, Product> {
    private Mapper<ProductDetailsCreationDto, ProductDetails> mapper;

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
    @Qualifier("productDetailsMapper")
    public void setMapper(Mapper<ProductDetailsCreationDto, ProductDetails> mapper) {
        this.mapper = mapper;
    }
}
