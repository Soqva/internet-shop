package com.s0qva.application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"details"})
public class Product {
    private Long id;

    private String name;

    private Double price;

    @JsonManagedReference
    private ProductDetails details;

    public void addDetails(ProductDetails details) {
        details.setProduct(this);
        this.details = details;
    }
}
