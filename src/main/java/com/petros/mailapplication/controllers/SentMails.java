package com.petros.mailapplication.controllers;

import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/sentmails")
public class SentMails {

    @Autowired
    UserService userService;

    @GetMapping
    public String sentMails(Model model, Principal principal) {
        com.petros.mailapplication.mail.SentMails.read(principal.getName(),userService.findByEmail(principal.getName()).getEmailPassword());
        return "redirect:/sendmail";
    }
}
