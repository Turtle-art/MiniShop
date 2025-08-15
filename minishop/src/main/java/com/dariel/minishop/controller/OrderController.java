package com.dariel.minishop.controller;

import com.dariel.minishop.service.impl.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/orders")
public class OrderController {

    private final CheckoutService checkoutService;

    public OrderController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<String> checkout(@PathVariable Long userId) {
        return ResponseEntity.ok(checkoutService.checkout(userId));
    }
}
