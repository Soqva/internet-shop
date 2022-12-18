package com.s0qva.application.service;

import com.s0qva.application.model.User;
import com.s0qva.application.model.UserRole;
import com.s0qva.application.model.dictionary.DictionaryRole;
import com.s0qva.application.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoleService {
    private final DictionaryRoleService dictionaryRoleService;
    private final UserRoleRepository userRoleRepository;

    public UserRole getByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId).orElse(null);
    }

    @Transactional
    public UserRole createOrUpdate(Long userId) {
        var defaultRole = dictionaryRoleService.getDefaultRole();

        return createOrUpdate(userId, defaultRole.getId());
    }

    @Transactional
    public UserRole createOrUpdate(Long userId, Long roleId) {
        var userRole = mapToEntity(userId, roleId);

        return saveOrUpdateUserRole(userRole);
    }

    private UserRole saveOrUpdateUserRole(UserRole userRole) {
        var existingUserRole = getByUserIdAndRoleId(
                userRole.getUser().getId(),
                userRole.getRole().getId()
        );
        if (!ObjectUtils.isEmpty(existingUserRole)) {
            userRole.setId(existingUserRole.getId());
        }
        return userRoleRepository.save(userRole);
    }

    private UserRole mapToEntity(Long userId, Long roleId) {
        return UserRole.builder()
                .user(User.builder().id(userId).build())
                .role(DictionaryRole.builder().id(roleId).build())
                .build();
    }
}
