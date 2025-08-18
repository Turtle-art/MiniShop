package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Order;
import com.dariel.minishop.model.OrderItem;
import com.dariel.minishop.model.User;
import com.dariel.minishop.model.enums.OrderStatus;
import com.dariel.minishop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createPendingOrder(User user, List<OrderItem> items) {
        log.info("Creating pending order for User ID: {}", user.getUserId());
        log.debug("Order items count: {}", items.size());

        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderTrackingId(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        log.info("Pending order created with Order ID: {} and Tracking ID: {}", savedOrder.getOrderId(), savedOrder.getOrderTrackingId());

        return savedOrder;
    }

    public void markOrderAsPaid(Long orderId) {
        log.info("Marking Order ID {} as PAID", orderId);
        orderRepository.findById(orderId).ifPresentOrElse(order -> {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
            log.info("Order ID {} successfully marked as PAID", orderId);
        }, () -> log.warn("Order ID {} not found, cannot mark as PAID", orderId));
    }
}