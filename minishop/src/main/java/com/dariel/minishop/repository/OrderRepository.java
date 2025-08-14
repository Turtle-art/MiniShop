package com.dariel.minishop.repository;

import com.dariel.minishop.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
