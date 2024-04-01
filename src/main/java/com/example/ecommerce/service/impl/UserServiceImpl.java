package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.RegistrationDto;
import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity saveUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPhoneNumber(registrationDto.getPhoneNumber());
        userEntity.setAddress(registrationDto.getAddress());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        userEntity.setRoles(Collections.singletonList(role));
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "ROLE_USER".equals(role.getName())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long userId) {
        UserEntity user = userRepository.getById(userId);
        Role role = roleRepository.getReferenceById(1L);
        user.getRoles().remove(role);
        userRepository.deleteById(userId);
    }

    @Override
    public void save(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(registrationDto.getId());
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userEntity.setAddress(registrationDto.getAddress());
        userEntity.setPhoneNumber(registrationDto.getPhoneNumber());
        Role role = roleRepository.findByName("ROLE_USER");
        userEntity.setRoles(Collections.singletonList(role));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public boolean isUser(List<Role> roles) {
        return roles.stream().noneMatch((role) -> role.getName().equals("ROLE_USER"));
    }

    @Override
    public boolean isKitchenStaff(List<Role> roles) {
        return roles.stream().noneMatch((role) -> role.getName().equals("ROLE_KITCHEN_STAFF"));
    }

    @Override
    public boolean isCashier(List<Role> roles) {
        return roles.stream().noneMatch((role) -> role.getName().equals("ROLE_CASHIER"));
    }

    @Override
    public boolean isDelivery(List<Role> roles) {
        return roles.stream().noneMatch((role) -> role.getName().equals("ROLE_DELIVERY"));
    }

    @Override
    public List<UserEntity> findAllStaff() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "ROLE_STAFF".equals(role.getName())))
                .collect(Collectors.toList());
    }
}
