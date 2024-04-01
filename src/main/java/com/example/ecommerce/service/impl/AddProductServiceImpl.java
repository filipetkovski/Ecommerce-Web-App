package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.repository.AddProductRepository;
import com.example.ecommerce.service.AddProductService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ecommerce.mapper.AddProductMapper.mapToAddProductDto;

@Service
public class AddProductServiceImpl implements AddProductService {
    AddProductRepository addProductRepository;

    public AddProductServiceImpl(AddProductRepository addProductRepository) {
        this.addProductRepository = addProductRepository;
    }

    @Override
    public AddProductDto findById(Long product) {
        return mapToAddProductDto(addProductRepository.getById(product));
    }

    @Override
    public void deleteProducts(List<Long> productIds) {
        addProductRepository.deleteAllById(productIds);
    }
}
