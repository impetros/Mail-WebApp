package com.petros.mailapplication.service;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//import com.petros.mailapplication.Util.HibernateUtil;
import com.petros.mailapplication.dto.UserRegistrationDto;
import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.Role;
import com.petros.mailapplication.model.User;
import com.petros.mailapplication.repository.MailRepository;
import com.petros.mailapplication.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration) {
        User user = new User();
        BeanUtils.copyProperties(registration,user);
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    public User addMails(String email, List<Mail> mails){
        User user=userRepository.findByEmail(email);
        user.addMails(mails);
        return userRepository.save(user);
    }

    public void deleteMail(long id){
        mailRepository.deleteById(id);
    }

    public List<Mail> getMails(String email,int tip){
        User user=findByEmail(email);
        return mailRepository.findMailByUserAndTip(user,tip);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }




    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}