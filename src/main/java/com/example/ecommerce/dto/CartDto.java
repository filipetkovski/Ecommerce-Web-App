package com.example.ecommerce.dto;

import com.example.ecommerce.entity.AddProduct;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long Id;
    private Double totalPrice = 0.0;
    private UserEntity createdBy;
    private List<AddProduct> cartProducts = new ArrayList<>();
}
