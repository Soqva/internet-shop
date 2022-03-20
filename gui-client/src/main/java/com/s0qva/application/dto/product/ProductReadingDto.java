package com.s0qva.application.dto.product;

import com.s0qva.application.dto.ReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReadingDto implements ReadingDto {
    private Long id;
    private String name;
    private Double price;
    private ProductDetailsReadingDto details;

    @Override
    public String toString() {
        return "Product: " + name + "- price: " + price + "\n";
    }
}
