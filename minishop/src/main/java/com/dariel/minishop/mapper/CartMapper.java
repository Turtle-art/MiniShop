package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.ProductDto;
import com.dariel.minishop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends ModelMapper<Product, ProductDto> {
    @Override
    public ProductDto mapFrom(Product product) {
        return null;
    }

    @Override
    public Product mapTo(ProductDto productDto) {
        return null;
    }
}
