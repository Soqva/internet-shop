package com.s0qva.application.service;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.mapper.UserMapper;
import com.s0qva.application.model.User;
import com.s0qva.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllAdmins() {
        return userRepository.findAllAdmins().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public UserDto getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToDto)
                .orElse(null);
    }

    private UserDto mapToDto(User user) {
        return UserMapper.mapToDto(user);
    }

    private User mapToEntity(UserDto userDto) {
        return UserMapper.mapToEntity(userDto);
    }
}
