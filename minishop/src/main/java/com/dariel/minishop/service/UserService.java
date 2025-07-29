package com.dariel.minishop.service;

import com.dariel.minishop.model.User;

public interface UserService {
    Iterable<User> getAllUsers();

    User findUserById(long userId);

    User findUserByEmail(String emailAddress);

    User createNewUser(User user);

    void deleteById(long userId);
}
