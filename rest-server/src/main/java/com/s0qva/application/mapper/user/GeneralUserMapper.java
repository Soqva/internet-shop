package com.s0qva.application.mapper.user;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.mapper.user.UserCreationToUserMapper;
import com.s0qva.application.mapper.user.UserToReadingMapper;
import com.s0qva.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralUserMapper {
    private final UserCreationToUserMapper userCreationToUserMapper;
    private final UserToReadingMapper userToReadingMapper;
    private final UserIdToUserMapper userIdToUserMapper;
    private final UserToUserIdMapper userToUserIdMapper;

    @Autowired
    public GeneralUserMapper(UserCreationToUserMapper userCreationToUserMapper,
                             UserToReadingMapper userToReadingMapper,
                             UserIdToUserMapper userIdToUserMapper,
                             UserToUserIdMapper userToUserIdMapper) {
        this.userCreationToUserMapper = userCreationToUserMapper;
        this.userToReadingMapper = userToReadingMapper;
        this.userIdToUserMapper = userIdToUserMapper;
        this.userToUserIdMapper = userToUserIdMapper;
    }

    public UserReadingDto mapUserToUserReadingDto(User user) {
        return userToReadingMapper.map(user);
    }

    public User mapUserCreationDtoToUser(UserCreationDto userCreationDto) {
        return userCreationToUserMapper.map(userCreationDto);
    }

    public User mapUserIdDtoToUser(UserIdDto userIdDto) {
        return userIdToUserMapper.map(userIdDto);
    }

    public UserIdDto mapUserToUserIdDto(User user) {
        return userToUserIdMapper.map(user);
    }
}
