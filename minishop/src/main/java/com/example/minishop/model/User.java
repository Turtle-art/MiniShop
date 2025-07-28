package com.example.minishop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
