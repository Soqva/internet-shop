package com.s0qva.application.dto.order;

import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.model.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreationDto {
    @NotNull(message = "the order must has an order date")
    private LocalDateTime orderDate;

    private OrderStatus status;

    @NotNull(message = "the order must be associated with a specific user")
    private UserIdDto userId;

    @NotNull(message = "the order must has a list of products")
    private List<ProductIdDto> products;
}
