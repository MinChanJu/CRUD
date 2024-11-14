package com.example.CRUD.repository;

import com.example.CRUD.model.Example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaExampleRepository extends JpaRepository<Example, Long> {
    List<Example> findAllByProblemId(long problemId);
    void deleteAllByProblemId(long problemId);
}