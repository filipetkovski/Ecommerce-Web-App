package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.entity.Order;

public class OrderMapper {
    public static Order mapToOrder(OrderDto orderDto) {
        Order order = Order.builder()
                .Id(orderDto.getId())
                .createdOn(orderDto.getCreatedOn())
                .orderProducts(orderDto.getOrderProducts())
                .updatedOn(orderDto.getUpdatedOn())
                .price(orderDto.getPrice())
                .createdBy(orderDto.getCreatedBy())
                .code(orderDto.getCode())
                .status(orderDto.getStatus())
                .build();
        return order;
    }

    public static OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = OrderDto.builder()
                .Id(order.getId())
                .createdOn(order.getCreatedOn())
                .orderProducts(order.getOrderProducts())
                .updatedOn(order.getUpdatedOn())
                .price(order.getPrice())
                .createdBy(order.getCreatedBy())
                .code(order.getCode())
                .status(order.getStatus())
                .build();
        return orderDto;
    }
}
