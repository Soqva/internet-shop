package com.s0qva.application.dto;

import com.s0qva.application.model.dictionary.DictionaryOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private DictionaryOrderStatus orderStatus;
    private Map<Integer, CommodityDto> orderedCommodities;
    private UserDto user;
}
