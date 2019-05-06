package com.petros.mailapplication;

import com.petros.mailapplication.dto.UserRegistrationDto;

import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        userRegistrationDto.setFirstName("Dobrescu");
        userRegistrationDto.setLastName("Vlad");
        userRegistrationDto.setConfirmEmail("vladdob14@gmail.com");
        userRegistrationDto.setEmail("vladdob14@gmail.com");
        userRegistrationDto.setEmailPassword("a!234567");
        userRegistrationDto.setPassword("123");
        userRegistrationDto.setConfirmPassword("123");
        userRegistrationDto.setTerms(true);
        userService.save(userRegistrationDto);

        UserRegistrationDto userRegistrationDto2=new UserRegistrationDto();
        userRegistrationDto2.setFirstName("Popa");
        userRegistrationDto2.setLastName("Ana");
        userRegistrationDto2.setConfirmEmail("anapopa0014@gmail.com");
        userRegistrationDto2.setEmail("anapopa0014@gmail.com");
        userRegistrationDto2.setEmailPassword("a!234567");
        userRegistrationDto2.setPassword("123");
        userRegistrationDto2.setConfirmPassword("123");
        userRegistrationDto2.setTerms(true);
        userService.save(userRegistrationDto2);
    }
}
