package com.petros.mailapplication.service.implementation;

import com.petros.mailapplication.io.entity.UserEntity;
import com.petros.mailapplication.repositories.UserRepository;
import com.petros.mailapplication.service.UserService;
import com.petros.mailapplication.shared.Utils;
import com.petros.mailapplication.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;


    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail())!=null)
            throw new RuntimeException("user already exists");

        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        userEntity.setUserId(utils.generateUserId(10));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        UserEntity storedUserDetails= userRepository.save(userEntity);
        UserDto returnValue=new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
