package com.dariel.minishop.repository;

import com.dariel.minishop.model.CartItem;
import com.dariel.minishop.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.user.userId = :userId")
    List<CartItem> findByUser(@Param("userId") Long userId);

    @Query("SELECT c FROM CartItem c WHERE c.user.userId = :userId AND c.product.productId = :productId")
    Optional<CartItem> findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);
}
