package com.s0qva.application.dto.user;

import com.s0qva.application.dto.order.OrderReadingDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserReadingDto {
    private Long id;
    private String name;
    private List<OrderReadingDto> orders;
}
