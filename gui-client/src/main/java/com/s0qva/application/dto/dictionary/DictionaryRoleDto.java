package com.s0qva.application.dto.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryRoleDto {
    private Long id;
    private String name;
    private String shortName;
    private String description;
}
