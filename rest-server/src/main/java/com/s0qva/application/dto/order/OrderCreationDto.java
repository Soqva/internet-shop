package com.s0qva.application.dto.order;

import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.user.UserIdDto;
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
    @NotNull(message = "the order must have an order date")
    private LocalDateTime orderDate;

    @NotNull(message = "the order must be associated with a specific user")
    private UserIdDto userId;

    @NotNull(message = "the order must have a list of products")
    private List<ProductIdDto> products;
}
