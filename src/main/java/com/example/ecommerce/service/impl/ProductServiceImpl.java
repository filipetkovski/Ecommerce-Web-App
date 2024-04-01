package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.AddProductRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ecommerce.mapper.ProductMapper.mapToProduct;
import static com.example.ecommerce.mapper.ProductMapper.mapToProductDto;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    AddProductRepository addProductRepository;

    public ProductServiceImpl(ProductRepository productRepository, AddProductRepository addProductRepository) {
        this.productRepository = productRepository;
        this.addProductRepository = addProductRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProductDto productDto) {
        LocalDateTime date = LocalDateTime.now();
        StringBuilder code = new StringBuilder(date.getYear() + "" + date.getMonthValue() + "" + date.getDayOfMonth() + "" + date.getHour() + "" + date.getMinute() + "" + date.getSecond());
        while(code.length() < 14) {
            code.append("0");
        }
        productDto.setCode(code.toString());
        productRepository.save(mapToProduct(productDto));
    }

    @Override
    public List<ProductDto> findDonuts() {
        List<Product> products = productRepository.findByCategory("Donuts");
        return products.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findCoffee() {
        List<Product> products = productRepository.findByCategory("Coffee");
        return products.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long productId) {
        return mapToProductDto(productRepository.getById(productId));
    }

    @Override
    public void delete(ProductDto productDto) {
        productRepository.delete(mapToProduct(productDto));
    }

    @Override
    public List<ProductDto> findDrinks() {
        List<Product> products = productRepository.findByCategory("Drinks");
        return products.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllById(List<Long> productIds) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(product -> productIds.contains(product.getId()))
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findByName(String code) {
        return mapToProductDto(productRepository.findByName(code));
    }

    @Override
    public List<ProductDto> findBySearch(String code) {
        return productRepository.findAll().stream().filter(product -> product.getName().contains(code)).map(ProductMapper::mapToProductDto).toList();
    }
}
