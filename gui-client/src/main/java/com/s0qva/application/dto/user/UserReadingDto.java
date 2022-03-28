package com.s0qva.application.dto.user;

import com.s0qva.application.dto.ReadingDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadingDto implements ReadingDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean banned;
    private List<OrderReadingDto> orders;

    @Override
    public String toString() {
        return "id: " + id
                + ", username: " + username
                + ", full name: " + firstName + " " + lastName
                + ", role: " + role
                + " banned: " + banned
                + ", amount of orders: " + orders.size();
    }
}
