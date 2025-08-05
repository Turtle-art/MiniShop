package com.dariel.minishop.dto;

import com.dariel.minishop.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class    CartItemDto {
    private Long cartItemId;
    private Long userId;
    private Product product;
    private int quantity;
}
