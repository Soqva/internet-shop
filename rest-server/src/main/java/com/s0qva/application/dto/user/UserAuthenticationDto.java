package com.s0qva.application.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationDto {
    @NotBlank(message = "the user must has a specific username")
    private String username;
    @NotBlank(message = "the user must has a specific password")
    private String password;
}
