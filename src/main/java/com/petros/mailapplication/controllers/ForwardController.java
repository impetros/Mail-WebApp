package com.petros.mailapplication.controllers;


import com.petros.mailapplication.forms.ComposeMail;
import com.petros.mailapplication.forms.ReplyMailForm;
import com.petros.mailapplication.mail.ForwardMail;
import com.petros.mailapplication.mail.ReplyMail;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static com.petros.mailapplication.controllers.InboxController.findIndex;

@Controller
@RequestMapping("/forward")
public class ForwardController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public String replyForm(Model model, @PathVariable long id, Principal principal){
        ComposeMail composeMail=new ComposeMail();
        composeMail.setId(id);
        model.addAttribute("composeMail", composeMail);
        return "forward";
    }

    @PostMapping("/{id}")
    public String replyMailSubmit(@ModelAttribute ComposeMail composeMail, @PathVariable long id, Principal principal) {
        String username=principal.getName();
        List<Mail>mails= userService.getMails(username,1);
        int index=findIndex(mails,id);
        List<String>emailAddress= Arrays.asList(composeMail.getEmailsAddresses().split(" "));
        for(String mail : emailAddress)
             ForwardMail.forward(username,userService.findByEmail(username).getEmailPassword(),mail,mails.get(index),composeMail);
        return "redirect:/inbox";
    }
}
