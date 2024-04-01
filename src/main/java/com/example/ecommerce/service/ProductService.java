package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    void save(ProductDto productDto);

    List<ProductDto> findDonuts();
    List<ProductDto> findCoffee();

    ProductDto findById(Long productId);

    void delete(ProductDto productDto);

    List<ProductDto> findDrinks();

    List<ProductDto> getAllById(List<Long> productIds);

    ProductDto findByName(String code);
    List<ProductDto> findBySearch(String code);
}
