package com.petros.mailapplication.controllers;

import com.petros.mailapplication.forms.ComposeMail;
import com.petros.mailapplication.forms.ReplyMailForm;
import com.petros.mailapplication.mail.ReplyMail;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public String replyForm(Model model,Principal principal){
        ReplyMail.reply(principal.getName(),userService.findByEmail(principal.getName()).getEmailPassword(),new Mail(),new ReplyMailForm());
        model.addAttribute("composeMail", new ComposeMail());
        return "reply";
    }
}
