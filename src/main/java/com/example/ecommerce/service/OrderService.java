package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.entity.AddProduct;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.UserEntity;

import java.util.List;

public interface OrderService {
    void makeOrder(List<AddProduct> addProducts, UserEntity user, double price);

    List<OrderDto> getByUser(UserEntity user);

    List<OrderDto> getWaitingOrders();

    void deleteById(Long orderId);

    OrderDto findById(Long orderId);

    void markAsDone(Long orderId);

    Order findByCode(String code);

    void markAsDelivering(Long orderId);

    List<OrderDto> getDeliveringOrders();
}
