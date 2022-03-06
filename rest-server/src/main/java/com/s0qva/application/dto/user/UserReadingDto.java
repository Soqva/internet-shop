package com.s0qva.application.dto.user;

import com.s0qva.application.dto.order.OrderReadingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadingDto {
    private Long id;
    private String name;
    private List<OrderReadingDto> orders;
}
