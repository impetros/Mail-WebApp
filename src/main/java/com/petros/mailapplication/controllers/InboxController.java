package com.petros.mailapplication.controllers;


import com.petros.mailapplication.mail.CheckingMails;
import com.petros.mailapplication.model.Mail;
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
@RequestMapping("/inbox")
public class InboxController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String myinbox(Principal principal, Model model){
        String username=principal.getName();
        List<Mail> mails=userService.getMails(username,1);
        model.addAttribute("mails", maxCharacters(mails));
        return "inbox";
    }

    @GetMapping("/refresh")
    public String refreshInbox(Principal principal,Model model){
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String username=principal.getName();
        String password=userService.findByEmail(username).getEmailPassword();
        long id=userService.findByEmail(username).getId();
        Set<Mail> mails=CheckingMails.check(host, mailStoreType, username, password,id);
        userService.addMails(username,mails,1);
        model.addAttribute("mails", userService.getMails(username,1));
        return "redirect:/inbox";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id, Principal principal,Model model){
        String username=principal.getName();
        userService.deleteMail(id);
        model.addAttribute("mails", userService.getMails(username,1));
        return "redirect:/inbox";
    }

    @GetMapping("/{id}")
    public String mail(@PathVariable long id,Principal principal,Model model){
        String username=principal.getName();
       List<Mail>mails= userService.getMails(username,1);
        int index=findIndex(mails,id);
        model.addAttribute("mail",mails.get(index));
        return "inboxmail";
    }

    public static int findIndex(List<Mail> mails,long id){
        int index=0;
        for(Mail mail : mails){
            if(mail.getId()==id)
                index=mails.indexOf(mail);
        }
        return index;
    }

    public static List<Mail> maxCharacters(List<Mail> mails){
        for(Mail mail : mails){
            if(mail.getText().length()>10){
                mails.get(mails.indexOf(mail)).setText(mail.getText().trim().substring(0,7)+"...");
            }
        }
        return mails;
    }
}
