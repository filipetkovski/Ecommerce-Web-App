package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long Id;
    private String code;
    private String name;
    private String category;
    private String photoUrl;
    private Double price;
    private String description;
    private Integer quantity;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
