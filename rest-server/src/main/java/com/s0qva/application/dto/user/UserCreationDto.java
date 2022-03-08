package com.s0qva.application.dto.user;

import com.s0qva.application.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationDto {
    @NotBlank(message = "the user must has a specific username")
    private String username;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean banned;
}
