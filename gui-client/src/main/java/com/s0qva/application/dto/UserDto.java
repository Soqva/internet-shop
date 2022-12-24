package com.s0qva.application.dto;

import com.s0qva.application.dto.dictionary.DictionaryRoleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.NONE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Getter(value = NONE)
    private Boolean blocked;
    private List<DictionaryRoleDto> roles;

    public boolean isBlocked() {
        return blocked;
    }
}
