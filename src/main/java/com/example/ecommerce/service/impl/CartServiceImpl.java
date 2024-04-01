package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.AddProduct;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.repository.AddProductRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ecommerce.mapper.AddProductMapper.mapToAddProduct;
import static com.example.ecommerce.mapper.CartMapper.mapToCartDto;
import static com.example.ecommerce.mapper.ProductMapper.mapToProduct;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

@Service
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    UserRepository userRepository;
    AddProductRepository addProductRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, AddProductRepository addProductRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.addProductRepository = addProductRepository;
    }

    @Override
    public void save(UserEntity user, ProductDto productDto, Integer quantity) {
        Cart cart = user.getCart();
        List<AddProduct> products = cart.getCartProducts();
        AddProduct hasAddProduct = products.stream().filter((product) -> product.getProduct().getId().equals(mapToProduct(productDto).getId())).findFirst().orElse(null);
        if(hasAddProduct != null) {
            hasAddProduct.setQuantity_needed(hasAddProduct.getQuantity_needed()+quantity);
            addProductRepository.save(hasAddProduct);
        } else {
            AddProduct addProduct = new AddProduct();
            addProduct.setProduct(mapToProduct(productDto));
            addProduct.setQuantity_needed(quantity);
            addProductRepository.save(addProduct);
            products.add(addProduct);
            cart.setCartProducts(products);
        }
        cart.setTotalPrice(round(cart.getTotalPrice()+(quantity*productDto.getPrice()),2));
        cartRepository.save(cart);
    }

    @Override
    public void remove(AddProductDto addProductDto, UserEntity user) {
        AddProduct addProduct = mapToAddProduct(addProductDto);
        Cart cart = user.getCart();
        List<AddProduct> products = cart.getCartProducts();
        Iterator<AddProduct> iterator = products.iterator();
        while (iterator.hasNext()) {
            AddProduct product = iterator.next();
            if (product.getId().equals(addProduct.getId())) {
                iterator.remove();
                cart.setTotalPrice(cart.getTotalPrice() - (addProductDto.getQuantity_needed() * addProductDto.getProduct().getPrice()));
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public CartDto getByCreatedBy(UserEntity user) {
        if(user.getCart() != null) {
            Cart cart = user.getCart();
            if(cart != null)
                return mapToCartDto(cart);
        }
        return null;
    }

    @Override
    public void deleteById(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void saveCart(UserEntity user) {
        Cart cart = new Cart();
        cart.setCreatedBy(user);
        cartRepository.save(cart);
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.getById(cartId);
    }

    @Override
    public void deleteProducts(Cart cart) {
        cart.setCartProducts(new ArrayList<>());
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}
