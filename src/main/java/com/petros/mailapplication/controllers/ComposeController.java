package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.mail.SendMail;
import com.petros.mailapplication.model.ComposeMail;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.repository.UserRepository;
import com.petros.mailapplication.service.UserService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/sendmail")
public class ComposeController {

    @Autowired
    UserService userService;

    @GetMapping
    public String composeMailForm(Model model) {
        model.addAttribute("composeMail", new ComposeMail());
        return "sendmail";
    }

    @PostMapping
    public String composeMailSubmit(@ModelAttribute ComposeMail composeMail,Principal principal) {
        SendMail.sendMail("pop.gmail.com","pop3",principal.getName(),userService.findByEmail(principal.getName()).getEmailPassword(),
                composeMail.getToEmailAdress(),composeMail.getSubject(),composeMail.getText());
        return "sendmail";
    }
}
