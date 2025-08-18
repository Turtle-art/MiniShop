package com.dariel.minishop.service.impl;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripeWebhookService {

    @Value("${stripe.webhookSecret}")
    private String webhookSecret;

    private final OrderService orderService;

    public StripeWebhookService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void handleEvent(String payload, String sigHeader) {
        try {
            log.debug("Received Stripe webhook payload: {}", payload);

            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            log.info("Stripe webhook event received: {}", event.getType());

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

                if (session != null && session.getMetadata().containsKey("orderId")) {
                    Long orderId = Long.valueOf(session.getMetadata().get("orderId"));
                    log.info("Checkout session completed for Order ID: {}", orderId);

                    orderService.markOrderAsPaid(orderId);
                    log.info("Order {} marked as PAID", orderId);
                } else {
                    log.warn("Checkout session missing or no orderId in metadata");
                }
            } else {
                log.debug("Unhandled Stripe event type: {}", event.getType());
            }
        } catch (Exception e) {
            log.error("Stripe webhook processing failed", e);
            throw new RuntimeException("Stripe webhook error", e);
        }
    }
}