package com.s0qva.application.session;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.model.enumeration.UserRole;
import lombok.Data;

import java.util.List;

@Data
public final class UserSession {
    private static final UserSession INSTANCE = new UserSession();
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean banned;
    private List<OrderReadingDto> orders;

    private UserSession() {
    }

    public static UserSession getInstance() {
        return INSTANCE;
    }

    public void createUserSession(UserReadingDto user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.banned = user.isBanned();
        this.orders = user.getOrders();
    }

    public void closeUserSession() {
        this.id = null;
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.role = null;
        this.banned = false;
        this.orders = null;
    }
}

