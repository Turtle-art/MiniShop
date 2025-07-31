package com.dariel.minishop.controller;

import com.dariel.minishop.dto.OrderDto;
import com.dariel.minishop.mapper.OrderMapper;
import com.dariel.minishop.model.Order;
import com.dariel.minishop.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PaymentService paymentService;
    private final OrderMapper orderMapper;

    public OrderController(PaymentService paymentService, OrderMapper orderMapper) {
        this.paymentService = paymentService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody OrderDto orderDto) {
        Order order = orderMapper.mapTo(orderDto);
        String sessionUrl = paymentService.createCheckoutSession(order);
        return ResponseEntity.ok(sessionUrl);
    }
}

