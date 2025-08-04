package com.dariel.minishop.repository;

import com.dariel.minishop.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<CartItem, Long> {
}
