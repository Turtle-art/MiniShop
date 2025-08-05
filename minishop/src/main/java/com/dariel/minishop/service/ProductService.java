package com.dariel.minishop.service;

import com.dariel.minishop.model.Product;

public interface ProductService {
    Iterable<Product> getAllProducts();
    Product createProduct(Product product);
    void deleteProduct(Product product);
    Product findProductByName(String productName);
}
