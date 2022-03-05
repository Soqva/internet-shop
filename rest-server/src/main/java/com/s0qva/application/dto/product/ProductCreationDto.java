package com.s0qva.application.dto.product;

import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationDto {
    @NotBlank(message = "Name is required attribute")
    private String name;

    @NotNull(message = "Price is required attribute")
    @DecimalMin(value = "0", message = "Price must be non-negative")
    private Double price;

    private ProductDetailsCreationDto details;
}
