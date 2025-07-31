package com.dariel.minishop.dto;

import com.dariel.minishop.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private Long orderItemId;
    private Product product;
    private int quantity;
    private long price;
    private String currency;
}
