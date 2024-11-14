package com.example.CRUD.service;

import com.example.CRUD.model.Example;
import com.example.CRUD.repository.JpaExampleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleService {
    
    private final JpaExampleRepository exampleRepository;
    private Example elseExample = new Example();

    @Autowired
    public ExampleService(JpaExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
        elseExample.setId(-1);
    }

    public long createExample(Example example) {
        exampleRepository.save(example);
        return example.getId();
    }

    public Example getExampleById(long id) {
        return exampleRepository.findById(id).orElseGet(() -> elseExample);
    }

    public List<Example> getAllExamplesByProblemId(long problemId) {
        return exampleRepository.findAllByProblemId(problemId);
    } 

    public void deleteExampleById(long id) {
        exampleRepository.deleteById(id);
    }

    public void deleteExampleByProblemId(long problemId){
        exampleRepository.deleteAllByProblemId(problemId);
    }
}
