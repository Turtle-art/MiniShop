package com.dariel.minishop.controller;

import com.dariel.minishop.dto.UserDto;
import com.dariel.minishop.mapper.UserMapper;
import com.dariel.minishop.model.User;
import com.dariel.minishop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Create New User", operationId = "CreateNewUser")
    @PostMapping("/create")
    public UserDto createNewUser(@RequestBody UserDto userDto) {
        User newUser = userMapper.mapTo(userDto);
        newUser = userService.createNewUser(newUser);
        return userMapper.mapFrom(newUser);
    }
}
