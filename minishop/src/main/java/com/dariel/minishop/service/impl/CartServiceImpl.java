package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Product;
import com.dariel.minishop.repository.CartRepository;
import com.dariel.minishop.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Product addItemToCart(Product item) {
        var existingCartItem = findProductById(item.getProductId());
        if (existingCartItem != null) {
            return existingCartItem;
        }
        return cartRepository.save(user);
    }

    private Product findProductById(Long productId) {
        return null;
    }

    @Override
    public List<Product> getCartItems(long userId) {
        return cartRepository.findByUser(userId);
    }

    @Override
    public void deleteCartItem(Product item) {
        if (!cartRepository.existsById(item.getProductId())) {
            throw new EntityNotFoundException("Cannot find Product with Id " + item.getProductId());
        }
        cartRepository.deleteById(item.getProductId());
    }

    @Override
    public void clearCart(long userId) {

    }
}
