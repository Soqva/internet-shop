package com.s0qva.application.dto;

import com.s0qva.application.dto.dictionary.DictionaryCountryDto;
import com.s0qva.application.model.dictionary.DictionaryCountry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityDto {
    private Long id;
    private String name;
    private String description;
    private Double cost;
    private Integer amount;
    private Integer availableAmount;
    private DictionaryCountryDto producingCountry;
}
