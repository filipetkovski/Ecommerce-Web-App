package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.repository.AddProductRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ecommerce.mapper.CartMapper.mapToCartDto;
import static com.example.ecommerce.mapper.OrderMapper.mapToOrderDto;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void makeOrder(List<AddProduct> addProducts, UserEntity user, double price) {
        Order order = new Order();
        order.setOrderProducts(addProducts);
        order.setPrice(price);
        List<Role> roles = user.getRoles().stream().filter((role) -> role.getName().equals("ROLE_CASHIER")).collect(Collectors.toList());
        if(!roles.isEmpty()) {
            order.setStatus("HERE");
        }
        order.setCreatedBy(user);
        LocalDateTime date = LocalDateTime.now();
        String code = date.getSecond() + "" + date.getMinute() + "" + date.getHour() + "" + date.getDayOfMonth() + "" + date.getMonthValue() + "" + date.getYear();
        while(code.length() < 14) {
            code += "0";
        }
        order.setCode(code);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getByUser(UserEntity user) {
        if(!user.getOrders().isEmpty()) {
            List<Order> orders = user.getOrders();
            if(orders != null)
                return orders.stream().map((order) -> mapToOrderDto(order)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<OrderDto> getWaitingOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((order) -> mapToOrderDto(order)).filter(orderDto -> orderDto.getStatus().equals("WAITING...") || orderDto.getStatus().equals("HERE")).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderDto findById(Long orderId) {
        return mapToOrderDto(orderRepository.getReferenceById(orderId));
    }

    @Override
    public void markAsDone(Long orderId) {
        Order order = orderRepository.getById(orderId);
        order.setStatus("DELIVERED");
        orderRepository.save(order);
    }

    @Override
    public Order findByCode(String code) {
        return orderRepository.findByCode(code);
    }

    @Override
    public void markAsDelivering(Long orderId) {
        Order order = orderRepository.getById(orderId);
        order.setStatus("DELIVERING");
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getDeliveringOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((order) -> mapToOrderDto(order)).filter(orderDto -> orderDto.getStatus().equals("DELIVERING")).collect(Collectors.toList());
    }
}
