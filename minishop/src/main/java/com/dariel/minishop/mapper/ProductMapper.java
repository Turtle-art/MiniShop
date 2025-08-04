package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.ProductDto;
import com.dariel.minishop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends ModelMapper<Product, ProductDto> {
    @Override
    public ProductDto mapFrom(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .userId(product.getLastModifiedByUserId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .build();
    }

    @Override
    public Product mapTo(ProductDto productDto) {
        return new Product(productDto.getProductId(),productDto.getUserId(), productDto.getName(), productDto.getDescription(),productDto.getPrice(), 0, productDto.getImageUrl(), productDto.getCategory());
    }
}
