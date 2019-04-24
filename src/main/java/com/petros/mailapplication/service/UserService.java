package com.petros.mailapplication.service;

import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}