package com.dariel.minishop.controller;

import com.dariel.minishop.dto.ProductDto;
import com.dariel.minishop.mapper.CartMapper;
import com.dariel.minishop.model.Product;
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
    public ProductDto AddItemToCart(@RequestBody ProductDto item) {
        Product newProduct = cartMapper.mapTo(item);
        newProduct = cartService.addItemToCart(newProduct);
        return cartMapper.mapFrom(newProduct);
    }

    @Operation(summary = "Get Items for user", operationId = "GetCartItems")
    @GetMapping("/{id}")
    public List<ProductDto> getCartItemsByUserId(@PathVariable long userId) {
        List<Product> productList = cartService.getCartItems(userId);
        List<ProductDto> productDtosList = new ArrayList<>();
        for (Product product: productList){
            productDtosList.add(cartMapper.mapFrom(product));
        }
        return productDtosList;
    }

    @Operation(summary = "Delete Item from cart", operationId = "DeleteItemFromCart")
    @DeleteMapping("/delete")
    public void deleteItemFromCart(@RequestBody ProductDto item) {
        Product itemToDelete = cartMapper.mapTo(item);
        cartService.deleteCartItem(itemToDelete);
    }

    @Operation(summary = "Clear user cart", operationId = "ClearUserCart")
    @DeleteMapping("/{id}")
    public void clearUserCart(@RequestBody long id) {
        cartService.clearCart(id);
    }
}
