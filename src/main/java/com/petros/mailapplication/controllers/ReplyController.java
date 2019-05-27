package com.petros.mailapplication.controllers;

import com.petros.mailapplication.forms.ComposeMail;
import com.petros.mailapplication.forms.ReplyMailForm;
import com.petros.mailapplication.mail.ReplyMail;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import static com.petros.mailapplication.controllers.InboxController.findIndex;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public String replyForm(Model model,@PathVariable long id,Principal principal){
        String username=principal.getName();
        List<Mail>mails= userService.getMails(username,1);
        int index=findIndex(mails,id);
        ReplyMailForm replyMailForm=new ReplyMailForm();
        replyMailForm.setId(id);
        replyMailForm.setTo(mails.get(index).getOtherEmail());
        model.addAttribute("replyMail", replyMailForm);
        return "reply";
    }

    @PostMapping("/{id}")
    public String replyMailSubmit(@ModelAttribute ReplyMailForm replyMailForm,@PathVariable long id, Principal principal) {
        String username=principal.getName();
        List<Mail>mails= userService.getMails(username,1);
        int index=findIndex(mails,id);
        ReplyMail.reply(username,userService.findByEmail(username).getEmailPassword(),mails.get(index),replyMailForm);
        return "redirect:/inbox";
    }
}
