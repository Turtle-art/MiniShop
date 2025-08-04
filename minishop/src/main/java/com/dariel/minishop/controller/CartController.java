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

    @Operation(summary = "Add Product to cart", operationId = "AddProductToCart")
    @PostMapping("/add")
    public ProductDto AddProductToCart(@RequestBody ProductDto productDto) {
        Product newProduct = cartMapper.mapTo(productDto);
        newProduct = cartService.addProductToCart(newProduct);
        return cartMapper.mapFrom(newProduct);
    }

    @Operation(summary = "Get Products for user", operationId = "GetCartProducts")
    @GetMapping("/{id}")
    public List<ProductDto> getCartProductsByUserId(@PathVariable long userId) {
        List<Product> productList = cartService.getCartProducts(userId);
        List<ProductDto> productDtosList = new ArrayList<>();
        for (Product product: productList){
            productDtosList.add(cartMapper.mapFrom(product));
        }
        return productDtosList;
    }

    @Operation(summary = "Delete Product from cart", operationId = "DeleteProductFromCart")
    @DeleteMapping("/delete")
    public void deleteProductFromCart(@RequestBody ProductDto productDto) {
        Product ProductToDelete = cartMapper.mapTo(productDto);
        cartService.deleteCartProduct(ProductToDelete);
    }

    @Operation(summary = "Clear user cart", operationId = "ClearUserCart")
    @DeleteMapping("/{id}")
    public void clearUserCart(@RequestBody long id) {
        cartService.clearCart(id);
    }
}
