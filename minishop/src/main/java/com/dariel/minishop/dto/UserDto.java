package com.dariel.minishop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String userName;
    private String role;

    public UserDto(long userId, String email, String userName, String role){
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.role = role;
    }
}
