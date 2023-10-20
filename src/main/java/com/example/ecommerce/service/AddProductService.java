package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.entity.AddProduct;

import java.util.List;

public interface AddProductService {
    AddProductDto findById(Long product);

    void deleteProducts(List<Long> productIds);
}
