package com.dariel.minishop.controller;

import com.dariel.minishop.dto.ProductDto;
import com.dariel.minishop.mapper.ProductMapper;
import com.dariel.minishop.model.Product;
import com.dariel.minishop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/api/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Operation(summary = "Get all Products")
    @GetMapping
    public List<ProductDto> getAllProducts() {
        var productList = productService.getAllProducts();
        List<ProductDto> productDtosList = new ArrayList<>();
        for (Product product: productList){
            productDtosList.add(productMapper.mapFrom(product));
        }
        return productDtosList;
    }

    @Operation(summary = "Add Product to db", operationId = "AddProductToDB")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto addProductToDb(@RequestBody ProductDto productDto) {
        Product newProduct = productMapper.mapTo(productDto);
        newProduct = productService.createProduct(newProduct);
        return productMapper.mapFrom(newProduct);
    }

    @Operation(summary = "Delete Product by id", operationId = "DeleteProductById")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProductById(@RequestBody ProductDto productDto) {
        Product productToDelete = productMapper.mapTo(productDto);
        productService.deleteProduct(productToDelete);
    }
}
