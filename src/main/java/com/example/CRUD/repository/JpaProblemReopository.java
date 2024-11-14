package com.example.CRUD.repository;

import com.example.CRUD.model.Problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProblemReopository extends JpaRepository<Problem, Long> {
    Optional<Problem> findBytitle(String title);
}
