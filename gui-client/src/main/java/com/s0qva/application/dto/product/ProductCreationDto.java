package com.s0qva.application.dto.product;

import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationDto {
    private String name;
    private Double price;
    private ProductDetailsCreationDto details;
}
