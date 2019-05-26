package com.petros.mailapplication.controllers;

import com.petros.mailapplication.mail.DeleteMail;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/sentmails")
public class SentMailsController {

    @Autowired
    UserService userService;


    @GetMapping
    public String sentMails(Model model, Principal principal) {
        List<Mail> mails=userService.getMails(principal.getName(),2);
        model.addAttribute("mails", InboxController.maxCharacters(mails));
        return "sentmails";
    }

    @GetMapping("/refresh")
    public String refreshSentMails(Principal principal){
        String username=principal.getName();
        User user=userService.findByEmail(username);
        userService.deleteAllMails(user,2);
        return "redirect:/sentmails/intermediate";
    }

    @GetMapping("/intermediate")
    public String intermediateInbox(Principal principal,Model model){
        Set<Mail> mails= com.petros.mailapplication.mail.SentMails.read(principal.getName(),userService.findByEmail(principal.getName()).getEmailPassword(),userService.findByEmail(principal.getName()).getId());
        userService.addMails(principal.getName(),mails,2);
        return "redirect:/sentmails";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id,Principal principal){
        String username=principal.getName();
        userService.deleteMail(userService.findByEmail(username),id);
        return "redirect:/sentmails";
    }

    @GetMapping("/{id}")
    public String mail(@PathVariable long id,Principal principal,Model model){
        String username=principal.getName();
        List<Mail>mails= userService.getMails(username,2);
        int index=InboxController.findIndex(mails,id);
        model.addAttribute("mail",mails.get(index));
        return "sentmail";
    }
}
