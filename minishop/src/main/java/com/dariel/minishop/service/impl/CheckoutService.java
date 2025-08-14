package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Order;
import com.dariel.minishop.model.OrderItem;
import com.dariel.minishop.service.CartService;
import com.dariel.minishop.service.PaymentService;
import com.dariel.minishop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;

    public CheckoutService(CartService cartService, OrderService orderService,
                           PaymentService paymentService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public String checkout(Long userId) {
        var cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        var user = userService.findUserById(userId);

        // Convert to order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            item.setCurrency("usd");
            return item;
        }).toList();

        Order order = orderService.createPendingOrder(user, orderItems);

        String sessionUrl = paymentService.createCheckoutSession(order);

        cartService.clearCart(userId);

        return sessionUrl;
    }
}

