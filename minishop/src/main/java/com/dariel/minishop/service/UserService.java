package com.dariel.minishop.service;

import com.dariel.minishop.model.User;

import java.util.Optional;

public interface UserService {
    User findUserById(long userId);

    Optional<User> findUserByEmail(String email);

    User createNewUser(User user);

    void deleteById(long userId);

    String getUserRole(long userId);
}
