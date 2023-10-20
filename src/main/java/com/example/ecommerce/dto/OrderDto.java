package com.example.ecommerce.dto;

import com.example.ecommerce.entity.AddProduct;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long Id;
    private String code;
    private Double price;
    private UserEntity createdBy;
    private List<AddProduct> orderProducts = new ArrayList<>();
    private String status = "NOT DONE";
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
