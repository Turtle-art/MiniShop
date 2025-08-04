package com.dariel.minishop.mapper;

import com.dariel.minishop.dto.OrderDto;
import com.dariel.minishop.model.Order;
import com.dariel.minishop.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper extends ModelMapper<Order, OrderDto> {

    @Override
    public OrderDto mapFrom(Order order) {
        return OrderDto.builder()
                .userDto(order.getUser())
                .orderDate(order.getOrderDate())
                .items(order.getItems())
                .paymentId(order.getPaymentId())
                .shippingAddress(order.getShippingAddress())
                .orderTrackingId(order.getOrderTrackingId())
                .build();
    }

    @Override
    public Order mapTo(OrderDto orderDto) {
        Order order = new Order();
        order.setUser(orderDto.getUserDto());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPaymentId(orderDto.getPaymentId());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setOrderTrackingId(orderDto.getOrderTrackingId());

        List<OrderItem> items = orderDto.getItems();
        if (items != null) {
            for (OrderItem item : items) {
                item.setOrder(order);
            }
        }

        order.setItems(items);
        return order;
    }
}