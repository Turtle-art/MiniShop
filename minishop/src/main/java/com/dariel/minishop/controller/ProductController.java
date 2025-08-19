package com.dariel.minishop.controller;

import com.dariel.minishop.dto.ProductDto;
import com.dariel.minishop.mapper.ProductMapper;
import com.dariel.minishop.model.Product;
import com.dariel.minishop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Get all Products")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var productList = productService.getAllProducts();
        List<ProductDto> productDtosList = new ArrayList<>();
        for (Product product : productList) {
            productDtosList.add(productMapper.mapFrom(product));
        }
        return ResponseEntity.ok(productDtosList);
    }

    @Operation(summary = "Add Product to db", operationId = "AddProductToDB")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductDto> addProductToDb(@RequestBody ProductDto productDto) {
        Product newProduct = productMapper.mapTo(productDto);
        newProduct = productService.createProduct(newProduct);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productMapper.mapFrom(newProduct));
    }

    @Operation(summary = "Delete Product by id", operationId = "DeleteProductById")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProductById(@RequestBody ProductDto productDto) {
        Product productToDelete = productMapper.mapTo(productDto);
        productService.deleteProduct(productToDelete);
        return ResponseEntity.noContent().build();
    }
}