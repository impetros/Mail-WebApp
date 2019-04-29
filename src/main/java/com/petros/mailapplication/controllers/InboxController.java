package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inbox")
public class InboxController {
    @GetMapping
    public String myinbox(){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "conttestaredegeaba@gmail.com";// change accordingly
        String password = "a!234567";// change accordingly
        CheckingMails.check(host, mailStoreType, username, password);
        return "inbox";
    }
}
