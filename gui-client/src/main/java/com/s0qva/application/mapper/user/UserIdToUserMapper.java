package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserIdToUserMapper implements Mapper<UserIdDto, User> {

    @Override
    public User map(UserIdDto userIdDto) {
        return User.builder()
                .id(userIdDto.getId())
                .build();
    }
}
