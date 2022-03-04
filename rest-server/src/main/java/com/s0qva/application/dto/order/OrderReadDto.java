package com.s0qva.application.dto.order;


import com.s0qva.application.model.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderReadDto {
    private LocalDateTime orderDate;
    private Long customerId;
    private List<Product> products;
}
