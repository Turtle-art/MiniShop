package com.dariel.minishop.controller;

import com.dariel.minishop.service.impl.StripeWebhookService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/api/webhook")
public class StripeWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    private final StripeWebhookService webhookService;

    public StripeWebhookController(StripeWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) throws IOException {
        logger.info("Received Stripe webhook request.");

        String payload = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));

        String sigHeader = request.getHeader("Stripe-Signature");

        try {
            webhookService.handleEvent(payload, sigHeader);
            logger.info("Successfully processed Stripe event.");
            return ResponseEntity.ok("Received");
        } catch (Exception e) {
            logger.error("Error processing Stripe event: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Webhook Error");
        }
    }
}