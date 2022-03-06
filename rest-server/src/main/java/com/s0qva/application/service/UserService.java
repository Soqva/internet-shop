package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.model.User;
import com.s0qva.application.repository.UserRepository;
import com.s0qva.application.mapper.user.GeneralUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private GeneralUserMapper userMapper;

    public List<UserReadingDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserReadingDto)
                .collect(Collectors.toList());
    }

    public UserReadingDto getUser(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);

        return maybeUser.map(userMapper::mapUserToUserReadingDto)
                .orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));
    }

    public Long saveUser(UserCreationDto userCreationDto) {
        User user = userMapper.mapUserCreationDtoToUser(userCreationDto);
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public UserReadingDto updateUser(Long id, UserCreationDto userCreationDto) {
        Optional<User> maybeOldUser = userRepository.findById(id);
        User oldUser = maybeOldUser.orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));
        User newUser = userMapper.mapUserCreationDtoToUser(userCreationDto);

        oldUser.setName(newUser.getName());

        User updatedUser = userRepository.save(oldUser);

        return userMapper.mapUserToUserReadingDto(updatedUser);
    }

    public void deleteUser(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        User user = maybeUser.orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));

        userRepository.delete(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(GeneralUserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
