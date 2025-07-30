package com.dariel.minishop.model;

import com.dariel.minishop.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String email, String username, String role) {
        this.email = email;
        this.username = username;
        this.role = UserRole.valueOf(role);
        this.orders = new ArrayList<>();
    }
}
