package com.petros.mailapplication.repository;

import com.petros.mailapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityTransaction;

@Repository
public interface UserRepository extends JpaRepository < User, Long > {
    User findByEmail(String email);
}