package com.dariel.minishop.service;

import com.dariel.minishop.model.Product;

public interface ProductService {
    Iterable<Product> getAllProducts();
    Product createProduct(Product product);
    void deleteProductById(long productId);
    Product findProductByName(String productName);
}
