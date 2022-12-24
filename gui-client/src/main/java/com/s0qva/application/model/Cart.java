package com.s0qva.application.model;

import com.s0qva.application.dto.CommodityDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private static final Cart INSTANCE = new Cart();
    private final List<CommodityDto> commodities = new ArrayList<>();

    private Cart() {
    }

    public static Cart getInstance() {
        return INSTANCE;
    }

    public void addToCart(CommodityDto commodityDto) {
        commodities.add(commodityDto);
    }

    public void removeFromCart(CommodityDto commodityDto) {
        commodities.remove(commodityDto);
    }

    public void clearCart() {
        commodities.clear();
    }
}
