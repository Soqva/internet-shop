package com.s0qva.application.dto.product;

import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReadingDto {
    private Long id;
    private String name;
    private Double price;
    private ProductDetailsReadingDto details;
}
