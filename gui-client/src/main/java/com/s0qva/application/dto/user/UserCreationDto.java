package com.s0qva.application.dto.user;

import com.s0qva.application.dto.CreationDto;
import com.s0qva.application.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationDto implements CreationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean banned;
}
