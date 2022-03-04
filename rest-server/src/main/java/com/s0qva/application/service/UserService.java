package com.s0qva.application.service;

import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.exception.UnsavedUserHasIdException;
import com.s0qva.application.model.User;
import com.s0qva.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));
    }

    public Long saveUser(User user) {
        if (user.getId() != null) {
            throw new UnsavedUserHasIdException("It is wrong that unsaved user has id = " + user.getId());
        }

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
