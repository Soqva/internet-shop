package com.s0qva.application.dto.product;

import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationDto {
    @NotBlank(message = "the product must has a specific name")
    private String name;

    @NotNull(message = "the product must has a specific price")
    @PositiveOrZero(message = "the product's price must be non-negative")
    private Double price;

    private ProductDetailsCreationDto details;
}
