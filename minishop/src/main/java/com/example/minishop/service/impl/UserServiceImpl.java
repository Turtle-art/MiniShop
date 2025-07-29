package com.example.minishop.service.impl;

import com.example.minishop.model.User;
import com.example.minishop.repository.UserRepository;
import com.example.minishop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return null;
    }

    @Override
    public User findUserById(long userId) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        if (email != null) {
            return userRepository.findByEmailAddress(email);
        } else {
            return null;
        }
    }

    @Override
    public User createNewUser(User user) {
        var existingUser = findUserByEmail(user.getEmail());
        if (existingUser != null) {
            return existingUser;
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteById(long userId) {

    }
}
