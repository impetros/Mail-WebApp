package com.petros.mailapplication.repository;

import com.petros.mailapplication.model.Mail;
import com.petros.mailapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository  extends JpaRepository<Mail,Long> {
    void deleteById(long id);
}
