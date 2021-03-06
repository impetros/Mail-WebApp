package com.petros.mailapplication.controllers;
import javax.validation.Valid;

import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.mail.Authentication;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(!Authentication.succesAuthentication(userDto.getEmail(),userDto.getEmailPassword()))
            result.rejectValue("emailPassword",null,"Cannot connect to email address");

        if (result.hasErrors() ) {
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
}