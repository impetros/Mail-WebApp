package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.SendMail;
import com.petros.mailapplication.forms.ComposeMail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

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
    public String composeMailSubmit(@ModelAttribute ComposeMail composeMail,Principal principal, @RequestParam("files") MultipartFile[] files) {
        List<String>emailAddress=Arrays.asList(composeMail.getEmailsAddresses().split(" "));
        for(String mail : emailAddress)
            SendMail.sendMail("pop.gmail.com","pop3",principal.getName(),userService.findByEmail(principal.getName()).getEmailPassword(),
                    mail,composeMail.getSubject(),composeMail.getText(),composeMail.getFile());
        System.out.println(composeMail.getFile());
        return "redirect:sendmail";
    }
}
