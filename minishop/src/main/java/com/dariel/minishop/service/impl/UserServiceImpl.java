package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.User;
import com.dariel.minishop.model.enums.UserRole;
import com.dariel.minishop.repository.UserRepository;
import com.dariel.minishop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    public User findUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        if (email != null) {
            return userRepository.findByEmail(email);
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
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Cannot find User with Id " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public String getUserRole(long userId) {
        var existingUser = findUserById(userId);
        if (existingUser != null){
            return existingUser.getRole().name();
        }
        return null;
    }
}
