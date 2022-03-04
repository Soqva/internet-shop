package com.s0qva.application.dto.product.detail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsReadingDto {
    private Long id;
    private String description;
    private String madeIn;
}
