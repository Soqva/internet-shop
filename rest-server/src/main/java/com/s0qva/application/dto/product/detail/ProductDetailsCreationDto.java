package com.s0qva.application.dto.product.detail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsCreationDto {
    private String description;
    private String madeIn;
}
