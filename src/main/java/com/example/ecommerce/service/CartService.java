package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.UserEntity;


public interface CartService {

    void save(UserEntity user, ProductDto productDto, Integer quantity);

    void remove(AddProductDto addProductDto, UserEntity user);

    CartDto getByCreatedBy(UserEntity user);

    void deleteById(Long cartId);

    void saveCart(UserEntity user);

    Cart findById(Long cartId);

    void deleteProducts(Cart cart);
}
