package com.dariel.minishop.service;

import com.dariel.minishop.model.Order;

public interface PaymentService {
    public String createCheckoutSession(Order order);
}
