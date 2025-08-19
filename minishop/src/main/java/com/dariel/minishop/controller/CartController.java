package com.dariel.minishop.controller;

import com.dariel.minishop.dto.CartItemDto;
import com.dariel.minishop.mapper.CartMapper;
import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @Operation(summary = "Add Item to cart", operationId = "AddItemToCart")
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> AddItemToCart(@RequestBody CartItemDto cartItemDto) {
        CartItem cartItemToAdd = cartMapper.mapTo(cartItemDto);
        cartItemToAdd = cartService.addItemToCart(cartItemToAdd);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartMapper.mapFrom(cartItemToAdd));
    }

    @Operation(summary = "Get Cart Items for user", operationId = "GetCartItems")
    @GetMapping("/{id}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable long id) {
        var cartItems = cartService.getCartItems(id);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem item : cartItems) {
            cartItemDtos.add(cartMapper.mapFrom(item));
        }
        return ResponseEntity
                .ok(cartItemDtos);
    }

    @Operation(summary = "Delete Item from cart", operationId = "DeleteItemFromCart")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProductFromCart(@RequestBody CartItemDto cartItemDto) {
        CartItem itemToDelete = cartMapper.mapTo(cartItemDto);
        cartService.deleteCartItem(itemToDelete);
        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "Clear user cart", operationId = "ClearUserCart")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clearUserCart(@PathVariable long id) {
        cartService.clearCart(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}