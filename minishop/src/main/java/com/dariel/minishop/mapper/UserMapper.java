package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.UserDto;
import com.dariel.minishop.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ModelMapper<User, UserDto> {

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .role(String.valueOf(user.getRole()))
                .build();
    }
    @Override
    public User mapTo(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getUserName(), userDto.getRole());
    }

//    @Override
//    public void mapTo(User source, User destination) {
//        destination.setEmail(source.getEmail());
//        destination.setUsername(source.getUsername());
//        destination.setRole(source.getRole());
//    }
}