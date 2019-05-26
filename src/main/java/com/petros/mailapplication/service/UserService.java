package com.petros.mailapplication.service;

import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

//    List<Mail>
    User findByEmail(String email);
    User save(UserRegistrationDto registration);
    User addMails(String email, List<Mail> mails);
    List<Mail> getMails(String email,int tip);
    void deleteMail(long id);
}