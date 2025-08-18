package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.repository.CartRepository;
import com.dariel.minishop.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem addItemToCart(CartItem item) {
        log.info("Adding item '{}' to cart for User ID {}", item.getProduct().getName(), item.getUser().getUserId());
        CartItem existingCartItem = findItemInCart(item);

        if (existingCartItem != null) {
            int newQty = Math.addExact(existingCartItem.getQuantity(), item.getQuantity());
            existingCartItem.setQuantity(newQty);
            log.debug("Updated quantity for Product ID {}: {}", existingCartItem.getProduct().getProductId(), newQty);
            return cartRepository.save(existingCartItem);
        } else {
            log.debug("Item not in cart yet, saving new cart item for Product ID {}", item.getProduct().getProductId());
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
        log.info("Fetching cart items for User ID {}", userId);
        return cartRepository.findByUser(userId);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        log.info("Deleting cart item ID {} for User ID {}", cartItem.getCartItemId(), cartItem.getUser().getUserId());
        if (!cartRepository.existsById(cartItem.getCartItemId())) {
            log.error("CartItem ID {} not found", cartItem.getCartItemId());
            throw new EntityNotFoundException("Cannot find CartItem with Id " + cartItem.getCartItemId());
        }
        cartRepository.deleteById(cartItem.getCartItemId());
        log.info("CartItem ID {} deleted successfully", cartItem.getCartItemId());
    }

    @Override
    public void clearCart(long userId) {
        log.info("Clearing cart for User ID {}", userId);
        List<CartItem> cartItems = cartRepository.findByUser(userId);
        cartRepository.deleteAll(cartItems);
        log.info("Cart cleared for User ID {}", userId);
    }
}