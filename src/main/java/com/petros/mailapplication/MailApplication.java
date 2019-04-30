package com.petros.mailapplication;

import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.Role;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.repository.UserRepository;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MailApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
        userRegistrationDto.setFirstName("Cont2");
        userRegistrationDto.setLastName("Degeaba2");
//        userRegistrationDto.setPassword("a!234567");
//        userRegistrationDto.setConfirmPassword("a!234567");
        userRegistrationDto.setConfirmEmail("dj_smash46@yahoo.com");
        userRegistrationDto.setEmail("dj_smash46@yahoo.com");
        userRegistrationDto.setPassword("123");
        userRegistrationDto.setConfirmPassword("123");
        userRegistrationDto.setTerms(true);
        userService.save(userRegistrationDto);
    }
}
