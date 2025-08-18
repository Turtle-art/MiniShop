package com.dariel.minishop.controller;

import com.dariel.minishop.service.impl.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<String> checkout(@PathVariable Long userId) {
        return ResponseEntity.ok(checkoutService.checkout(userId));
    }
}
