package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.OrderDto;
import com.dariel.minishop.model.Order;

public class OrderMapper extends ModelMapper<Order, OrderDto> {
    @Override
    public OrderDto mapFrom(Order order) {
        return null;
    }

    @Override
    public Order mapTo(OrderDto orderDto) {
        return null;
    }
}
