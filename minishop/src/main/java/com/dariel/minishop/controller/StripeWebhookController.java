package com.dariel.minishop.controller;

import com.dariel.minishop.service.impl.StripeWebhookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/api/webhook")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final StripeWebhookService webhookService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) throws IOException {
        String payload = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));

        String sigHeader = request.getHeader("Stripe-Signature");

        try {
            webhookService.handleEvent(payload, sigHeader);
            return ResponseEntity.ok("Received");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook Error");
        }
    }
}