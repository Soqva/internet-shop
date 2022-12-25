package com.s0qva.application.repository;

import com.s0qva.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "select u from User u " +
            "join u.userRoles ur " +
            "where ur.role.name like 'admin'")
    List<User> findAllAdmins();
}
