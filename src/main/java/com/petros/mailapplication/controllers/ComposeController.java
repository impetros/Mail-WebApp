package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sendmail")
public class ComposeController {

    @GetMapping
    public String sendMail(){

        return "sendmail";}

}
