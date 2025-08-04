package com.dariel.minishop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private Long lastModifiedByUserId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    private String imageUrl;

    private String category;
}

