package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserIdMapper implements Mapper<User, UserIdDto> {

    @Override
    public UserIdDto map(User user) {
        return UserIdDto.builder()
                .id(user.getId())
                .build();
    }
}
