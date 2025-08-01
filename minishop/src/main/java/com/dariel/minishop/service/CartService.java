package com.dariel.minishop.service;

import com.dariel.minishop.model.Product;

import java.util.List;

public interface CartService {
    Product addItemToCart(Product item);
    List<Product> getCartItems(long userId);
    void deleteCartItem(Product item);
    void clearCart(long userId);
}
