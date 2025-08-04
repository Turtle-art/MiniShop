package com.dariel.minishop.service;

import com.dariel.minishop.model.User;
import com.dariel.minishop.model.enums.UserRole;

public interface UserService {
    User findUserById(long userId);

    User findUserByEmail(String email);

    User createNewUser(User user);

    void deleteById(long userId);

    String getUserRole(long userId);
}
