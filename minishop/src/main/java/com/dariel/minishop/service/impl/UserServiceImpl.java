package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.User;
import com.dariel.minishop.repository.UserRepository;
import com.dariel.minishop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(long userId) {
        log.debug("Looking up user by ID: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", userId);
                    return new EntityNotFoundException("User not found: " + userId);
                });
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        log.debug("Searching for user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.debug("No user found with email {}", email);
        } else {
            log.info("User {} found by email {}", user.getUserId(), email);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User createNewUser(User user) {
        log.info("Attempting to create new user with email: {}", user.getEmail());
        return findUserByEmail(user.getEmail())
                .orElseGet(() -> {
                    log.info("No existing user found with email {}, creating new one", user.getEmail());
                    User savedUser = userRepository.save(user);
                    log.info("New user created with ID {}", savedUser.getUserId());
                    return savedUser;
                });
    }

    @Override
    public void deleteById(long userId) {
        log.info("Attempting to delete user with ID {}", userId);
        if (!userRepository.existsById(userId)) {
            log.error("Cannot delete user, ID {} does not exist", userId);
            throw new EntityNotFoundException("Cannot find User with Id " + userId);
        }
        userRepository.deleteById(userId);
        log.info("User with ID {} successfully deleted", userId);
    }

    @Override
    public String getUserRole(long userId) {
        log.debug("Fetching role for user ID {}", userId);
        var existingUser = findUserById(userId);
        if (existingUser != null) {
            String role = existingUser.getRole().name();
            log.info("User {} has role {}", userId, role);
            return role;
        }
        log.warn("User with ID {} returned null when fetching role", userId);
        return null;
    }
}