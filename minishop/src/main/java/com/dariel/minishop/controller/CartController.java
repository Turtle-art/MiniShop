package com.dariel.minishop.controller;

import com.dariel.minishop.dto.CartItemDto;
import com.dariel.minishop.mapper.CartMapper;
import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper){
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @Operation(summary = "Add Item to cart", operationId = "AddItemToCart")
    @PostMapping("/add")
    public CartItemDto AddItemToCart(@RequestBody CartItemDto cartItemDto) {
        CartItem cartItemToAdd = cartMapper.mapTo(cartItemDto);
        cartItemToAdd = cartService.addItemToCart(cartItemToAdd);
        return cartMapper.mapFrom(cartItemToAdd);
    }

    @Operation(summary = "Get CartItems for user", operationId = "GetCartItems")
    @GetMapping("/{id}")
    public List<CartItemDto> getCartItems(@PathVariable long userId) {
        var cartItems = cartService.getCartItems(userId);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem item: cartItems){
            cartItemDtos.add(cartMapper.mapFrom(item));
        }
        return cartItemDtos;
    }

    @Operation(summary = "Delete Item from cart", operationId = "DeleteItemFromCart")
    @DeleteMapping("/delete")
    public void deleteProductFromCart(@RequestBody CartItemDto cartItemDto) {
        CartItem itemToDelete = cartMapper.mapTo(cartItemDto);
        cartService.deleteCartItem(itemToDelete);
    }

    @Operation(summary = "Clear user cart", operationId = "ClearUserCart")
    @DeleteMapping("/{id}")
    public void clearUserCart(@RequestBody long id) {
        cartService.clearCart(id);
    }
}
