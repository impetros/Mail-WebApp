package com.petros.mailapplication.repository;

import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository  extends JpaRepository<Mail,Long> {
    List<Mail> findMailByUserAndTip(User user,int tip);
    void deleteById(long id);
    void deleteAllByUserAndTip(User user,int tip);
}
