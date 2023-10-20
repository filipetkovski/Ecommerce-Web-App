package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.mapper.AddProductMapper;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.SecurityUtil;
import com.example.ecommerce.service.AddProductService;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ecommerce.mapper.AddProductMapper.mapToAddProduct;

@Controller
public class OrderController {
    OrderService orderService;
    CartService cartService;
    AddProductService addProductService;
    UserService userService;

    public OrderController(OrderService orderService, CartService cartService, UserService userService, AddProductService addProductService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
        this.addProductService = addProductService;
    }

    @GetMapping("/order/{cartId}")
    public String makeOrder() {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userService.findByEmail(email);
        if(user != null) {
            Cart cart = user.getCart();
            if(cart != null) {
                List<AddProduct> products = cart.getCartProducts().stream().map((addProduct) -> mapToAddProduct(addProductService.findById(addProduct.getId()))).collect(Collectors.toList());
                double price = cart.getTotalPrice();
                cartService.deleteProducts(cart);
                orderService.makeOrder(products, user, price);
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        String email = SecurityUtil.getSessionUser();
        UserEntity user =  userService.findByEmail(email);
        if(user != null) {
            List<OrderDto> orders = orderService.getByUser(user);
            if(orders == null) {
                model.addAttribute("empty",true);
            } else {
                model.addAttribute("empty",false);
                model.addAttribute("orders", orders);
            }
        }
        return "myorders";
    }

    @GetMapping("/all/orders")
    public String getWaitingOrders(Model model) {
        List<OrderDto> orders = orderService.getWaitingOrders();
        model.addAttribute("orders", orders);
        return "all_orders";
    }

    @GetMapping("/all/delivering/orders")
    public String getDeliveringOrders(Model model) {
        List<OrderDto> orders = orderService.getDeliveringOrders();
        model.addAttribute("orders", orders);
        return "all_delivering_orders";
    }

    @GetMapping("/order/{orderId}/view")
    public String getAnOrder(@PathVariable("orderId") Long orderId, Model model) {
        OrderDto order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order_view";
    }

    @GetMapping("/order/{orderId}/done")
    public String markAsDone(@PathVariable("orderId") Long orderId) {
        orderService.markAsDone(orderId);
        return "redirect:/all/orders";
    }

    @GetMapping("/order/{orderId}/delivering")
    public String markAsDelivering(@PathVariable("orderId") Long orderId) {
        orderService.markAsDelivering(orderId);
        return "redirect:/all/orders";
    }

    @GetMapping("/order/{orderId}/delete")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        List<AddProduct> addProducts = orderDto.getOrderProducts();
        List<Long> productsIds = addProducts.stream().map((product) -> product.getId()).collect(Collectors.toList());
        orderService.deleteById(orderId);
        addProductService.deleteProducts(productsIds);
        if(orderDto.getStatus().equals("DELIVERED")) {
            return "redirect:/all/delivering/orders";
        }
        return "redirect:/all/orders";
    }

    @PostMapping("/order/delivery/find")
    @ResponseBody
    public ResponseEntity<?> findOrderByCode(@RequestParam String code) {
        try {
            Order order = orderService.findByCode(code);
            if(!code.matches("\\d+")) {
                return ResponseEntity.ok("Invalid");
            }
            if (order == null) {
                return ResponseEntity.ok("NotFound");
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
