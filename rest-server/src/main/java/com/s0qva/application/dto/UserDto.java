package com.s0qva.application.dto;

import com.s0qva.application.model.dictionary.DictionaryRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean blocked;
    private List<DictionaryRole> roles;
}
