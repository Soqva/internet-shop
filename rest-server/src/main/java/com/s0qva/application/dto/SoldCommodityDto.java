package com.s0qva.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldCommodityDto {
    private Long id;
    private CommodityDto commodity;
    private Integer amountOfSoldCommodities;
    private Long soldDate;
}
