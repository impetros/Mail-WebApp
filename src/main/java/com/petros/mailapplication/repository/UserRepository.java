package com.petros.mailapplication.repository;

import com.petros.mailapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository < User, Long > {
    User findByEmail(String email);
}