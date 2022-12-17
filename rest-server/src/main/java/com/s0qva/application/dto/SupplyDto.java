package com.s0qva.application.dto;

import com.s0qva.application.dto.dictionary.DictionarySupplierDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDto {
    private Long id;
    private DictionarySupplierDto supplier;
    private Long receivingDate;
    private Map<Integer, CommodityDto> suppliedCommodities;
}
