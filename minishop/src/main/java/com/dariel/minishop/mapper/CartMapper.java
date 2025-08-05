package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.CartItemDto;
import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.model.User;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends ModelMapper<CartItem, CartItemDto> {

    @Override
    public CartItemDto mapFrom(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getCartItemId())
                .product(cartItem.getProduct())
                .userId(cartItem.getUser().getUserId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    @Override
    public CartItem mapTo(CartItemDto cartItemDto) {
        User user = new User();
        user.setUserId(cartItemDto.getUserId());
        return new CartItem(cartItemDto.getCartItemId(), user, cartItemDto.getProduct(), cartItemDto.getQuantity());
    }
}
