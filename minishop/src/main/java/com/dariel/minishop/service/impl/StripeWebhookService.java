package com.dariel.minishop.service.impl;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null && session.getMetadata().containsKey("orderId")) {
                    Long orderId = Long.valueOf(session.getMetadata().get("orderId"));
                    orderService.markOrderAsPaid(orderId);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Stripe webhook error", e);
        }
    }
}

