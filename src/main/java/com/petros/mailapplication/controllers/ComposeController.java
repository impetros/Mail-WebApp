package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.mail.SendMail;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sendmail")
public class ComposeController {

    @GetMapping
    public String sendMail(){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "vladdob14@gmail.com";// change accordingly
        String password = "a!234567";// change accordingly
        SendMail.sendMail(host,mailStoreType,username,password,"petros@mailinator.com");
        return "sendmail";}

}
