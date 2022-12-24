package com.s0qva.application.dto;

import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private DictionaryOrderStatusDto orderStatus;
    private List<CommodityDto> orderedCommodities;
    private Double orderCost;
    private UserDto user;
}
