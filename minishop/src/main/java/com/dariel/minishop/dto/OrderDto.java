package com.dariel.minishop.dto;

import com.dariel.minishop.model.OrderItem;
import com.dariel.minishop.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private User userDto;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private String paymentId;
    private String shippingAddress;
}
