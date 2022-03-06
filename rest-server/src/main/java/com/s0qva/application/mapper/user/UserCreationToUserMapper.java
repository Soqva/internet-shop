package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreationToUserMapper implements Mapper<UserCreationDto, User> {

    @Override
    public User map(UserCreationDto userCreationDto) {
        return User.builder()
                .name(userCreationDto.getName())
                .build();
    }
}
