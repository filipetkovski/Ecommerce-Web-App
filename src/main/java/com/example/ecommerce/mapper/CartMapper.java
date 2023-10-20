package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.entity.Cart;

public class CartMapper {

    public static Cart mapToCart(CartDto cartDto) {
        Cart cart = Cart.builder()
                .createdBy(cartDto.getCreatedBy())
                .Id(cartDto.getId())
                .cartProducts(cartDto.getCartProducts())
                .totalPrice(cartDto.getTotalPrice())
                .build();
        return cart;
    }

    public static CartDto mapToCartDto(Cart cart) {
        CartDto cartDto = CartDto.builder()
                .createdBy(cart.getCreatedBy())
                .Id(cart.getId())
                .cartProducts(cart.getCartProducts())
                .totalPrice(cart.getTotalPrice())
                .build();
        return cartDto;
    }
}
