package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Order;
import com.dariel.minishop.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StripePaymentService implements PaymentService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Value("${frontend.successUrl}")
    private String successUrl;

    @Value("${frontend.cancelUrl}")
    private String cancelUrl;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
        log.info("Stripe API key initialized successfully");
    }

    @Override
    public String createCheckoutSession(Order order) {
        try {
            log.info("Creating Stripe Checkout session for Order ID: {}", order.getOrderId());
            log.debug("Order details: User={}, TotalItems={}", order.getUser().getEmail(), order.getItems().size());

            List<SessionCreateParams.LineItem> lineItems = order.getItems().stream().map(item ->
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) item.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(item.getCurrency())
                                            .setUnitAmount(item.getPrice().multiply(BigDecimal.valueOf(100)).longValue())
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(item.getProduct().getName())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            ).collect(Collectors.toList());

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomerEmail(order.getUser().getEmail())
                    .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(cancelUrl)
                    .addAllLineItem(lineItems)
                    .putMetadata("orderId", String.valueOf(order.getOrderId()))
                    .build();

            Session session = Session.create(params);
            log.info("Stripe Checkout session created successfully for Order ID: {}", order.getOrderId());
            log.debug("Stripe Session ID: {}", session.getId());

            return session.getUrl();

        } catch (Exception e) {
            log.error("Error creating Stripe Checkout session for Order ID: {}", order.getOrderId(), e);
            throw new RuntimeException("Error creating Stripe Checkout session", e);
        }
    }
}