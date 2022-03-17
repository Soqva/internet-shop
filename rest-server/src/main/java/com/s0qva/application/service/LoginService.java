package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.exception.InvalidPasswordDuringSignInException;
import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.exception.model.enumeration.DefaultExceptionMessage;
import com.s0qva.application.mapper.user.GeneralUserMapper;
import com.s0qva.application.model.User;
import com.s0qva.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final GeneralUserMapper userMapper;

    @Autowired
    public LoginService(UserRepository userRepository, GeneralUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserReadingDto signIn(UserAuthenticationDto userAuthenticationDto) {
        Optional<User> maybeUser = userRepository.findByUsername(userAuthenticationDto.getUsername());

        User user = maybeUser.orElseThrow(() ->
                new NoSuchUserException(DefaultExceptionMessage.NO_SUCH_USER_WITH_USERNAME.getMessage()
                        + userAuthenticationDto.getUsername()));

        if (!user.getPassword().equals(userAuthenticationDto.getPassword())) {
            throw new InvalidPasswordDuringSignInException(DefaultExceptionMessage.INVALID_PASSWORD_DURING_SIGN_IN.getMessage());
        }

        return userMapper.mapUserToUserReadingDto(user);
    }
}
