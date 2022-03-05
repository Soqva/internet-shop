package com.s0qva.application.dto.user;

import com.s0qva.application.dto.order.OrderCreationDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserCreationDto {
    private String name;
    private List<OrderCreationDto> orders;
}
