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

//        UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
//        userRegistrationDto.setFirstName("Cont2");
//        userRegistrationDto.setLastName("Degeaba2");
//        userRegistrationDto.setConfirmEmail("ana@gmail.com");
//        userRegistrationDto.setEmail("ana@gmail.com");
//        userRegistrationDto.setPassword("123");
//        userRegistrationDto.setConfirmPassword("123");
//        userRegistrationDto.setEmailPassword("100000");
//        userRegistrationDto.setTerms(true);
//        userService.save(userRegistrationDto);
//
//        UserRegistrationDto userRegistration=new UserRegistrationDto();
//        userRegistration.setFirstName("Popa");
//        userRegistration.setLastName("Cristi");
//        userRegistration.setConfirmEmail("cristi@gmail.com");
//        userRegistration.setEmail("cristi@gmail.com");
//        userRegistration.setPassword("123");
//        userRegistration.setConfirmPassword("123");
//        userRegistration.setTerms(true);
//        userService.save(userRegistration);
//        userService.addMails("ana@gmail.com",Arrays.asList(new Mail("nimic@gmail.com","Subiect","Ce faci?"),new Mail("vlad@gmail.com","Suibect 2","Bine")));
//        userService.addMails("ana@gmail.com",Arrays.asList(new Mail("mihai@gmail.com","Subiect","Ce faci?"),new Mail("coco@gmail.com","Suibect 2","Bine")));
    }
}
