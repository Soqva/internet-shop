package com.s0qva.application.model;

import com.s0qva.application.dto.product.ProductReadingDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private static final Cart INSTANCE = new Cart();
    private final List<ProductReadingDto> products = new ArrayList<>();

    private Cart() {
    }

    public static Cart getInstance() {
        return INSTANCE;
    }

    public void addToCart(ProductReadingDto productReadingDto) {
        products.add(productReadingDto);
    }

    public void clearCart() {
        products.clear();
    }
}
