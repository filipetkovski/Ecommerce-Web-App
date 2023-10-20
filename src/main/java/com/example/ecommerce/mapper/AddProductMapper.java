package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.entity.AddProduct;

public class AddProductMapper {

    public static AddProduct mapToAddProduct(AddProductDto addProductDto) {
        AddProduct addProduct = AddProduct.builder()
                .product(addProductDto.getProduct())
                .Id(addProductDto.getId())
                .updatedOn(addProductDto.getUpdatedOn())
                .createdOn(addProductDto.getCreatedOn())
                .quantity_needed(addProductDto.getQuantity_needed())
                .cart(addProductDto.getCart())
                .order(addProductDto.getOrder())
                .build();
        return addProduct;
    }

    public static AddProductDto mapToAddProductDto(AddProduct addProduct) {
        AddProductDto addProductDto = AddProductDto.builder()
                .product(addProduct.getProduct())
                .Id(addProduct.getId())
                .updatedOn(addProduct.getUpdatedOn())
                .createdOn(addProduct.getCreatedOn())
                .quantity_needed(addProduct.getQuantity_needed())
                .product(addProduct.getProduct())
                .product(addProduct.getProduct())
                .build();
        return addProductDto;
    }
}
