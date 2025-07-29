package com.dariel.minishop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cartItems")
@Data
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

