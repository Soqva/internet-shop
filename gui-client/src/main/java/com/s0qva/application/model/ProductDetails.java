package com.s0qva.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.s0qva.application.model.enumeration.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product"})
public class ProductDetails {
    private Long id;

    private String description;

    private Country madeIn;

    @JsonBackReference
    private Product product;
}
