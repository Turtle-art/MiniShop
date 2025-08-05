package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.CartItem;
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
    public CartItem addItemToCart(CartItem item) {
        CartItem existingCartItem = findItemInCart(item);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + item.getQuantity());
            return cartRepository.save(existingCartItem);
        } else {
            return cartRepository.save(item);
        }
    }

    private CartItem findItemInCart(CartItem item) {
        return cartRepository.findByUserAndProduct(
                item.getUser().getUserId(),
                item.getProduct().getProductId()
        ).orElse(null);
    }

    @Override
    public List<CartItem> getCartItems(long userId) {
        return cartRepository.findByUser(userId);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        if (!cartRepository.existsById(cartItem.getCartItemId())) {
            throw new EntityNotFoundException("Cannot find CartItem with Id " + cartItem.getCartItemId());
        }
        cartRepository.deleteById(cartItem.getCartItemId());
    }

    @Override
    public void clearCart(long userId) {
        List<CartItem> cartItems = cartRepository.findByUser(userId);
        cartRepository.deleteAll(cartItems);
    }
}
