package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.mail.SendMail;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.repository.UserRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/sendmail")
public class ComposeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
//    @ResponseBody
    public String sendMail(Authentication authentication){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "vladdob14@gmail.com";// change accordingly
        String password = "a!234567";// change accordingly
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String us=((UserDetails)principal).getUsername();
//        String pass=((UserDetails)principal).getPassword();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String us=userDetails.getUsername();
        String pass=userDetails.getPassword();//imi intoarce null
//        User user = userRepository.findByEmail(us);
//        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//        String pass=userDetails.getPassword();
        System.out.println("user "+ us+" pass: "+pass);
        SendMail.sendMail(host,mailStoreType,username,password,"petros@mailinator.com");
        return "sendmail";}

}
