package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddProductDto;
import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.SecurityUtil;
import com.example.ecommerce.service.AddProductService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ecommerce.mapper.AddProductMapper.mapToAddProductDto;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.round;

@Controller
public class MyCartController {
    ProductService productService;
    CartService cartService;
    UserRepository userRepository;
    AddProductService addProductService;

    public MyCartController(ProductService productService, UserRepository userRepository, CartService cartService, AddProductService addProductService) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.addProductService = addProductService;
    }

    @GetMapping("/cart")
    public String getMyCart(Model model) {
        UserEntity user = getUser();
        if(user != null) {
            CartDto cartDto = cartService.getByCreatedBy(user);
            if(cartDto.getCartProducts().isEmpty()) {
                model.addAttribute("empty", true);
            } else {
                List<AddProductDto> addProducts = cartDto.getCartProducts().stream().map((product) -> mapToAddProductDto(product)).collect(Collectors.toList());
                model.addAttribute("empty", false);
                model.addAttribute("addProducts", addProducts);
            }
            model.addAttribute("total", round(cartDto.getTotalPrice(),2));
            model.addAttribute("cartId",cartDto.getId());
        }
        return "cart";
    }

    @GetMapping("/cart/add/{productId}/{quantity}")
    public String addToMyCart(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) {
        UserEntity user = getUser();
        if(user != null) {
            ProductDto productDto = productService.findById(productId);
            productDto.setQuantity(productDto.getQuantity()-quantity);
            productService.save(productDto);
            cartService.save(user, productDto, quantity);
            return "redirect:/menu/" + productDto.getCategory().toLowerCase();
        }
        return "redirect:/cart";
    }

    public UserEntity getUser() {
        String email = SecurityUtil.getSessionUser();
        return userRepository.findByEmail(email);
    }

    @GetMapping("/cart/{addProductId}/remove")
    public String removeFromCart(@PathVariable("addProductId") Long addProductId) {
        UserEntity user = getUser();
        if(user != null) {
            AddProductDto addProductDto = addProductService.findById(addProductId);
            cartService.remove(addProductDto, user);
            ProductDto productDto = productService.findById(addProductDto.getProduct().getId());
            productDto.setQuantity(productDto.getQuantity()+addProductDto.getQuantity_needed());
            productService.save(productDto);
        }
        return "redirect:/cart";
    }
}
