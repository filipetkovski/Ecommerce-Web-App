package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RegistrationDto;
import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.security.SecurityUtil;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;
    private CartService cartService;

    public AuthController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {
        UserEntity existedUserEmail = userService.findByEmail(user.getEmail());
        if(existedUserEmail != null && existedUserEmail.getEmail() != null && !existedUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }

        UserEntity existedUserUsername = userService.findByUsername(user.getUsername());
        if(existedUserUsername != null && existedUserUsername.getUsername() != null && !existedUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        UserEntity userEntity = userService.saveUser(user);
        cartService.saveCart(userEntity);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/manager")
    private  String getAllUsers(Model model) {
        List<UserEntity> users = userService.findAll();
        model.addAttribute("users", users);
        return "users_list";
    }

    @GetMapping("/staff/manager")
    private  String getAllStaff(Model model) {
        List<UserEntity> users = userService.findAllStaff();
        model.addAttribute("users", users);
        return "staff_list";
    }

    @GetMapping("/user/{userId}/delete")
    private String deleteUser(@PathVariable("userId") Long userId) {
        UserEntity user = userService.findById(userId);
        cartService.deleteById(user.getCart().getId());
        userService.deleteById(userId);
        return "redirect:/user/manager";
    }

    @GetMapping("/user/{userId}/edit")
    private String editUser(@PathVariable("userId") Long userId, Model model) {
        UserEntity user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping("/user/{userId}/edit")
    private String updateEditUser(@PathVariable("userId") Long userId, @ModelAttribute("user") RegistrationDto user) {
        user.setId(userId);
        userService.save(user);
        return "redirect:/user/manager?success";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }
}
