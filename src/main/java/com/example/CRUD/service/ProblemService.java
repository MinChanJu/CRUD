package com.example.CRUD.service;

import com.example.CRUD.model.Example;
import com.example.CRUD.model.Problem;
import com.example.CRUD.repository.JpaProblemReopository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    
    private final JpaProblemReopository problemReopository;
    private final ExampleService exampleService;
    private Problem elseProblem = new Problem();

    @Autowired
    public ProblemService(JpaProblemReopository problemReopository, ExampleService exampleService) {
        this.problemReopository = problemReopository;
        this.exampleService = exampleService;
        elseProblem.setId(-1);
    }

    public long createProblem(Problem problem) {
        Optional<Problem> findProblem = problemReopository.findBytitle(problem.getTitle());
        if (findProblem.isEmpty()) {
            problemReopository.save(problem);

            Example example = new Example();
            example.setProblemId(problem.getId());
            example.setInput(problem.getInput());
            example.setOutput(problem.getOutput());

            exampleService.createExample(example);
            return problem.getId();
        }
        return -1;
    }

    public Problem getProblemById(Long id) {
        return problemReopository.findById(id).orElseGet(() -> elseProblem);
    }

    public List<Problem> getAllProblems() {
        return problemReopository.findAll();
    }

    public Problem updatProblemById(Problem updateProblem) {
        Problem problem = problemReopository.findById(updateProblem.getId()).orElseGet(() -> elseProblem);
        problem.setTitle(updateProblem.getTitle());
        problem.setDescription(updateProblem.getDescription());
        problem.setInput(updateProblem.getInput());
        problem.setOutput(updateProblem.getOutput());

        problemReopository.save(problem);
        return problem;
    }

    public void deletProblemById(long id) {
        problemReopository.deleteById(id);
    }
}
