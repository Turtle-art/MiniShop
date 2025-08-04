package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Product;
import com.dariel.minishop.repository.ProductRepository;
import com.dariel.minishop.service.ProductService;
import com.dariel.minishop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    public ProductServiceImpl(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        var userRole = userService.getUserRole(product.getUserId());
        var existingProduct = findProductByName(product.getName());
        if (existingProduct != null) {
            return existingProduct;
        }
        else if (userRole.equals("ADMIN")){
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProductById(long productId) {
        if (!productRepository.existsById(productId)){
            throw new EntityNotFoundException("Cannot find Product with Id" + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Product findProductByName(String productName) {
        if (productName != null){
            return productRepository.findByName(productName);
        }
        else {
            return null;
        }
    }
}
