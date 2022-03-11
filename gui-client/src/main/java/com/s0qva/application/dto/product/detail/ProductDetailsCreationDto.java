package com.s0qva.application.dto.product.detail;

import com.s0qva.application.dto.CreationDto;
import com.s0qva.application.model.enumeration.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsCreationDto implements CreationDto {
    private String description;
    private Country madeIn;
}
