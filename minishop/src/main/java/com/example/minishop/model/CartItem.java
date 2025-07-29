package com.example.minishop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cartItems")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private int quantity;
}

