package com.s0qva.application.dto.order;

import com.s0qva.application.dto.product.ProductCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreationDto {
    private LocalDateTime orderDate;
    private List<ProductCreationDto> products;
}
