package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/inbox")
public class InboxController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String myinbox(Principal principal, Model model){
//        String host = "pop.gmail.com";
//        String mailStoreType = "pop3";
//        String username=principal.getName();
//        String password=userService.findByEmail(username).getEmailPassword();
//        long id=userService.findByEmail(username).getId();
//        List<Mail> mails=CheckingMails.check(host, mailStoreType, username, password,id);
//        if(mails.size()!=0)
//            userService.addMails(username,mails);
        String username=principal.getName();
        model.addAttribute("mails", userService.findByEmail(username).getMails());
        return "inbox";
    }
    @GetMapping("/refresh")
    public String refreshInbox(Principal principal,Model model){
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String username=principal.getName();
        String password=userService.findByEmail(username).getEmailPassword();
        long id=userService.findByEmail(username).getId();
        List<Mail> mails=CheckingMails.check(host, mailStoreType, username, password,id);
        if(mails.size()!=0)
            userService.addMails(username,mails);
        model.addAttribute("mails", userService.findByEmail(username).getMails());
        return "inbox";
    }
}
