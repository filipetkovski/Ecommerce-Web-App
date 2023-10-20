package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;

public class ProductMapper {

    public static ProductDto mapToProductDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .Id(product.getId())
                .category(product.getCategory())
                .createdOn(product.getCreatedOn())
                .name(product.getName())
                .photoUrl(product.getPhotoUrl())
                .price(product.getPrice())
                .updatedOn(product.getUpdatedOn())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .code(product.getCode())
                .build();
        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto) {
        Product product = Product.builder()
                .Id(productDto.getId())
                .category(productDto.getCategory())
                .createdOn(productDto.getCreatedOn())
                .name(productDto.getName())
                .photoUrl(productDto.getPhotoUrl())
                .price(productDto.getPrice())
                .updatedOn(productDto.getUpdatedOn())
                .description(productDto.getDescription())
                .quantity(productDto.getQuantity())
                .code(productDto.getCode())
                .build();
        return product;
    }
}
