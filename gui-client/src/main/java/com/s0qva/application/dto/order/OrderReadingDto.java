package com.s0qva.application.dto.order;


import com.s0qva.application.dto.ReadingDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.model.enumeration.OrderStatus;
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
public class OrderReadingDto implements ReadingDto {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private UserIdDto userId;
    private List<ProductReadingDto> products;
}
