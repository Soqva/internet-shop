package com.s0qva.application.service;

import com.s0qva.application.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
}
