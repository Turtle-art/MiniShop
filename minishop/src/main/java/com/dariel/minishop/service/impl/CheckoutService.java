package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Order;
import com.dariel.minishop.model.OrderItem;
import com.dariel.minishop.service.CartService;
import com.dariel.minishop.service.PaymentService;
import com.dariel.minishop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CheckoutService {

    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;

    @Value("${stripe.currency}")
    private String currency;

    public CheckoutService(CartService cartService, OrderService orderService,
                           PaymentService paymentService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public String checkout(Long userId) {
        log.info("Starting checkout for User ID: {}", userId);

        var cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            log.warn("Checkout failed: Cart is empty for User ID: {}", userId);
            throw new RuntimeException("Cart is empty");
        }

        var user = userService.findUserById(userId);
        log.debug("User details: ID={}, Email={}", user.getUserId(), user.getEmail());

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            item.setCurrency(currency);
            return item;
        }).toList();
        log.debug("Converted {} cart items to order items", orderItems.size());

        Order order = orderService.createPendingOrder(user, orderItems);
        log.info("Created pending order with ID {} for User ID {}", order.getOrderId(), userId);

        String sessionUrl = paymentService.createCheckoutSession(order);
        log.info("Stripe checkout session created for Order ID {}: {}", order.getOrderId(), sessionUrl);

        cartService.clearCart(userId);
        log.info("Cleared cart for User ID {}", userId);

        return sessionUrl;
    }
}