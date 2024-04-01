package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {
    private Long Id;
    private Product product;
    private Integer quantity_needed;
    private List<Order> order;
    private List<Cart> cart;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
