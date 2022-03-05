package com.s0qva.application.dto.product.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsReadingDto {
    private Long id;
    private String description;
    private String madeIn;
}
