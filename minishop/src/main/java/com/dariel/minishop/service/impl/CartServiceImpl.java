package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Product;
import com.dariel.minishop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public Product addItemToCart(Product item) {
        return null;
    }

    @Override
    public List<Product> getCartItems(long userId) {
        return null;
    }

    @Override
    public void deleteCartItem(Product item) {

    }

    @Override
    public void clearCart(long userId) {

    }
}
