package com.example.ecommerce.controller;

import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.SecurityUtil;
import com.example.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class GeneralController {
    private final UserRepository userRepository;
    private final UserService userService;

    public GeneralController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage() {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        if(user != null) {
            if(!userService.isUser(user.getRoles()))
                return "home_page";

            if(!userService.isKitchenStaff(user.getRoles()))
                return "redirect:/all/orders";

            if(!userService.isCashier(user.getRoles()))
                return "redirect:/cashier";

            if(!userService.isDelivery(user.getRoles()))
                return "redirect:/all/delivering/orders";

        }
        return "home_page";
    }
}
