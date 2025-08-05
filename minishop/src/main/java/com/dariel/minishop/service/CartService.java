package com.dariel.minishop.service;

import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.model.Product;

import java.util.List;

public interface CartService {
    CartItem addItemToCart(CartItem item);
    List<CartItem> getCartItems(long userId);
    void deleteCartItem(CartItem cartItem);
    void clearCart(long userId);
}
