package com.petros.mailapplication.service;

import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User save(UserRegistrationDto registration);
    User addMails(String email, Set<Mail> mails, int tip);
    List<Mail> getMails(String email,int tip);
    void deleteMail(User user,long id);
    void deleteMail(long id);
    void deleteAllMails(User user,int tip);
}