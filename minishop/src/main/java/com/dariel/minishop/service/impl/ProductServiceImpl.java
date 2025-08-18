package com.dariel.minishop.service.impl;

import com.dariel.minishop.model.Product;
import com.dariel.minishop.model.enums.UserRole;
import com.dariel.minishop.repository.ProductRepository;
import com.dariel.minishop.service.ProductService;
import com.dariel.minishop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Fetching all products from database");
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Attempting to create product: {}", product.getName());

        var userRole = userService.getUserRole(product.getUserId());
        log.debug("User ID {} has role {}", product.getUserId(), userRole);

        var existingProduct = findProductByName(product.getName());
        if (existingProduct != null) {
            log.warn("Product '{}' already exists. Returning existing product.", product.getName());
            return existingProduct;
        } else if (!UserRole.ADMIN.name().equals(userRole)) {
            log.error("User ID {} with role {} attempted to create product '{}', but lacks permission",
                    product.getUserId(), userRole, product.getName());
            throw new SecurityException("Insufficient permission");
        }

        var savedProduct = productRepository.save(product);
        log.info("Product '{}' created successfully with ID {}", savedProduct.getName(), savedProduct.getProductId());
        return savedProduct;
    }

    @Override
    public void deleteProduct(Product product) {
        log.info("Attempting to delete product ID {} by User ID {}", product.getProductId(), product.getUserId());

        var userRole = userService.getUserRole(product.getUserId());
        log.debug("User ID {} has role {}", product.getUserId(), userRole);

        if (!productRepository.existsById(product.getProductId())) {
            log.error("Product with ID {} not found. Cannot delete.", product.getProductId());
            throw new EntityNotFoundException("Cannot find Product with Id " + product.getProductId());
        } else if (!UserRole.ADMIN.name().equals(userRole)) {
            log.error("User ID {} with role {} attempted to delete product ID {}, but lacks permission",
                    product.getUserId(), userRole, product.getProductId());
            throw new SecurityException("Insufficient permission");
        }

        productRepository.deleteById(product.getProductId());
        log.info("Product with ID {} deleted successfully", product.getProductId());
    }

    @Override
    public Product findProductByName(String productName) {
        if (productName != null) {
            log.debug("Searching for product with name '{}'", productName);
            return productRepository.findByName(productName);
        } else {
            log.warn("Attempted to search for product with null name");
            return null;
        }
    }
}