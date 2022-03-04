package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.model.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProductReadingMapper implements Mapper<Product, ProductReadingDto> {
    private Mapper<ProductDetails, ProductDetailsReadingDto> productDetailsReadMapper;

    @Override
    public ProductReadingDto map(Product product) {
        return ProductReadingDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .details(productDetailsReadMapper.map(product.getDetails()))
                .build();
    }

    @Autowired
    @Qualifier("productDetailsReadingMapper")
    public void setProductDetailsReadMapper(Mapper<ProductDetails, ProductDetailsReadingDto> productDetailsReadMapper) {
        this.productDetailsReadMapper = productDetailsReadMapper;
    }
}
