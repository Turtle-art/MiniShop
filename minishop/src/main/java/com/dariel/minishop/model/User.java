package com.dariel.minishop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String email, String name, String role) {
        this(0, email, name, role);
    }

    public User(long userId, String emailAddress, String name, String role) {
        this.userId = userId;
        this.email = emailAddress;
        this.name = name;
        this.role = role;
    }
}
