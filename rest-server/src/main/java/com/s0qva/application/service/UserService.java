package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.mapper.user.UserCreationToUserMapper;
import com.s0qva.application.mapper.user.UserToReadingMapper;
import com.s0qva.application.model.Order;
import com.s0qva.application.model.Product;
import com.s0qva.application.model.User;
import com.s0qva.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserToReadingMapper userToReadingMapper;
    private UserCreationToUserMapper userCreationToUserMapper;

    public List<UserReadingDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userToReadingMapper::map)
                .collect(Collectors.toList());
    }

    public UserReadingDto getUser(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);

        return maybeUser.map(userToReadingMapper::map)
                .orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));
    }

    public Long saveUser(UserCreationDto userCreationDto) {
        User user = userCreationToUserMapper.map(userCreationDto);
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public UserReadingDto updateUser(Long id, UserCreationDto userCreationDto) {
        Optional<User> maybeOldUser = userRepository.findById(id);
        User newUser = userCreationToUserMapper.map(userCreationDto);

        User oldUser = maybeOldUser.orElseThrow(() -> new NoSuchUserException("There is no user with id = " + id));

        List<Order> oldOrders = oldUser.getOrders();
        List<Order> newOrders = newUser.getOrders();

        for (int i = 0; i < oldOrders.size(); i++) {
            Order oldOrder = oldOrders.get(i);
            Order newOrder = newOrders.get(i);

            newOrder.setId(oldOrder.getId());

            List<Product> oldProducts = oldOrder.getProducts();
            List<Product> newProducts = newOrder.getProducts();

            for (int j = 0; j < oldProducts.size(); j++) {
                Product oldProduct = oldProducts.get(j);
                Product newProduct = newProducts.get(j);

                newProduct.setId(oldProduct.getId());
            }
        }

        oldUser.setName(newUser.getName());
        oldUser.updateOrders(newOrders);

        User updatedUser = userRepository.save(oldUser);

        return userToReadingMapper.map(updatedUser);
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
    public void setUserReadingMapper(UserToReadingMapper userToReadingMapper) {
        this.userToReadingMapper = userToReadingMapper;
    }

    @Autowired
    public void setUserMapper(UserCreationToUserMapper userCreationToUserMapper) {
        this.userCreationToUserMapper = userCreationToUserMapper;
    }
}
