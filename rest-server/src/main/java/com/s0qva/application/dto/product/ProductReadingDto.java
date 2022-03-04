package com.s0qva.application.dto.product;

import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReadingDto {
    private Long id;
    private String name;
    private Double price;
    private ProductDetailsReadingDto details;
}
