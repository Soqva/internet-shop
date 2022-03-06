package com.s0qva.application.dto.order;


import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.user.UserIdDto;
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
public class OrderReadingDto {
    private Long id;
    private UserIdDto userId;
    private LocalDateTime orderDate;
    private List<ProductReadingDto> products;
}
