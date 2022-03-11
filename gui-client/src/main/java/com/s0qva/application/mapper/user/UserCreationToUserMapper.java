package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreationToUserMapper implements Mapper<UserCreationDto, User> {

    @Override
    public User map(UserCreationDto userCreationDto) {
        User user = User.builder()
                .username(userCreationDto.getUsername())
                .firstName(userCreationDto.getFirstName())
                .lastName(userCreationDto.getLastName())
                .banned(userCreationDto.isBanned())
                .build();

        if (userCreationDto.getRole() != null) {
            user.setRole(userCreationDto.getRole());
        }

        return user;
    }
}
