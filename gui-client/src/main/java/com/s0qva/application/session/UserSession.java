package com.s0qva.application.session;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.dto.dictionary.DictionaryRoleDto;
import com.s0qva.application.model.enumeration.UserRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

import javax.management.relation.Role;
import java.util.List;

import static lombok.AccessLevel.NONE;

@Data
public final class UserSession {
    private static final UserSession INSTANCE = new UserSession();
    private String token;
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    @Getter(value = NONE)
    private Boolean blocked;
    private List<DictionaryRoleDto> roles;

    private UserSession() {
    }

    public void createUserSession(String token, UserDto user) {
        this.token = token;
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles();
        this.blocked = user.isBlocked();
    }

    public void closeUserSession() {
        this.token = null;
        this.id = null;
        this.email = null;
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.roles = null;
        this.blocked = false;
    }

    public boolean containsRole(UserRole role) {
        return roles.stream()
                .map(DictionaryRoleDto::getName)
                .anyMatch(currentRoleName -> currentRoleName.equals(role.getName()));
    }

    public UserDto getUser() {
        return UserDto.builder()
                .id(id)
                .blocked(blocked)
                .build();
    }

    public HttpHeaders getBearerHttpHeader() {
        var httpHeaders = new HttpHeaders();

        httpHeaders.setBearerAuth(token);
        return httpHeaders;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public static UserSession getInstance() {
        return INSTANCE;
    }
}

