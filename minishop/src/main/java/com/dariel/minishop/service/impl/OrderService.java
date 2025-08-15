package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Order;
import com.dariel.minishop.model.OrderItem;
import com.dariel.minishop.model.User;
import com.dariel.minishop.model.enums.OrderStatus;
import com.dariel.minishop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createPendingOrder(User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderTrackingId(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        order.setStatus(OrderStatus.valueOf("PENDING"));
        return orderRepository.save(order);
    }

    public void markOrderAsPaid(Long orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(OrderStatus.valueOf("PAID"));
            orderRepository.save(order);
        });
    }
}
