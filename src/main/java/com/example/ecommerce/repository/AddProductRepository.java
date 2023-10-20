package com.example.ecommerce.repository;

import com.example.ecommerce.entity.AddProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddProductRepository extends JpaRepository<AddProduct, Long> {
    AddProduct getById(Long addProductId);
}
