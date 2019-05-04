package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inbox")
public class InboxController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String myinbox(){
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "vladdob14@gmail.com";// change accordingly
        String password = "a!234567";// change accordingly
//        List<Mail> mails=CheckingMails.check(host, mailStoreType, username, password);
//        System.out.println(mails);
        System.out.println(userService.findByEmail("vladdob14@gmail.com").getEmailPassword());
//        userService.deleteMails("ana@gmail.com"); //nu sterge din tabel
       // userService.addMails("ana@gmail.com",mails);
        return "inbox";
    }
}
