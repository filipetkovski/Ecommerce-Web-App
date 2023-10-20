package com.example.ecommerce.service;


import com.example.ecommerce.dto.RegistrationDto;
import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    List<UserEntity> findAll();
    void deleteById(Long userId);

    void save(RegistrationDto user);

    UserEntity findById(Long userId);

    boolean isUser(List<Role> roles);

    boolean isKitchenStaff(List<Role> roles);

    boolean isCashier(List<Role> roles);

    boolean isDelivery(List<Role> roles);

    List<UserEntity> findAllStaff();
}
