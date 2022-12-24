package com.s0qva.application.service;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.mapper.UserMapper;
import com.s0qva.application.model.User;
import com.s0qva.application.model.UserRole;
import com.s0qva.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var existingUser = mapToEntity(userService.getByUsername(username));

        if (ObjectUtils.isEmpty(existingUser)) {
            throw new UsernameNotFoundException("There is not user with this username");
        }
        return new org.springframework.security.core.userdetails.User(
                username,
                existingUser.getPassword(),
                !existingUser.isBlocked(),
                true,
                true,
                !existingUser.isBlocked(),
                existingUser.getRoles()
        );
    }

    @Transactional
    public Long signUp(UserDto userDto) {
        return saveUser(userDto).getId();
    }

    private UserDto saveUser(UserDto userDto) {
        var user = mapToEntity(userDto);
        var existingUser = userService.getByUsername(user.getUsername());

        if (!ObjectUtils.isEmpty(existingUser)) {
            throw new RuntimeException("The user already exists");
        }
        var createdUser = userRepository.save(user);
        var createdUserRole = saveUserRole(createdUser.getId());

        createdUserRole.setUser(createdUser);
        return mapToDto(createdUser);
    }

    private UserRole saveUserRole(Long userId) {
        return userRoleService.createOrUpdate(userId);
    }

    private User mapToEntity(UserDto userDto) {
        return UserMapper.mapToEntity(userDto);
    }

    private UserDto mapToDto(User user) {
        return UserMapper.mapToDto(user);
    }
}
