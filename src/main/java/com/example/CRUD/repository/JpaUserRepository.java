package com.example.CRUD.repository;

import com.example.CRUD.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserPw(String userPw);
}
