package com.s0qva.application.mapper.product;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralProductMapper {
    private final ProductCreationToProductMapper productCreationToProductMapper;
    private final ProductToReadingMapper productToReadingMapper;
    private final ProductIdToProductMapper productIdToProductMapper;
    private final ProductToProductIdMapper productToProductIdMapper;

    @Autowired
    public GeneralProductMapper(ProductCreationToProductMapper productCreationToProductMapper,
                                ProductToReadingMapper productToReadingMapper,
                                ProductIdToProductMapper productIdToProductMapper,
                                ProductToProductIdMapper productToProductIdMapper) {
        this.productCreationToProductMapper = productCreationToProductMapper;
        this.productToReadingMapper = productToReadingMapper;
        this.productIdToProductMapper = productIdToProductMapper;
        this.productToProductIdMapper = productToProductIdMapper;
    }

    public ProductReadingDto mapProductToProductReadingDto(Product product) {
        return productToReadingMapper.map(product);
    }

    public Product mapProductCreationDtoToProduct(ProductCreationDto productCreationDto) {
        return productCreationToProductMapper.map(productCreationDto);
    }

    public Product mapProductIdDtoToProduct(ProductIdDto productIdDto) {
        return productIdToProductMapper.map(productIdDto);
    }

    public ProductIdDto mapProductToProductIdDto(Product product) {
        return productToProductIdMapper.map(product);
    }
}
