package com.s0qva.application.service;

import com.s0qva.application.dto.AuthDto;
import com.s0qva.application.dto.UserDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.mapper.UserMapper;
import com.s0qva.application.model.User;
import com.s0qva.application.model.UserRole;
import com.s0qva.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var existingUser = userService.getByUsername(username);

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
    public Long signUp(AuthDto authDto) {
        var createdUserId = saveUser(authDto).getId();

        saveUserRole(createdUserId);
        return createdUserId;
    }

    private UserDto saveUser(AuthDto authDto) {
        var user = mapToEntity(authDto);
        var existingUser = userService.getByUsername(user.getUsername());

        if (!ObjectUtils.isEmpty(existingUser)) {
            throw new RuntimeException("The user already exists");
        }
        var createdUser = userRepository.save(user);

        return mapToDto(createdUser);
    }

    private UserRole saveUserRole(Long userId) {
        return userRoleService.createOrUpdate(userId);
    }

    private User mapToEntity(AuthDto authDto) {
        return DefaultMapper.mapToEntity(authDto, User.class);
    }

    private UserDto mapToDto(User user) {
        return UserMapper.mapToDto(user);
    }
}
