package com.example.CRUD.service;

import com.example.CRUD.model.Problem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProblemServiceTest {
    
    @Autowired ProblemService problemService;

    @Test
    void 문제추가() {
        Problem problem1 = new Problem();
        problem1.setTitle("testTitle");
        problem1.setDescription("description1");
        problem1.setInput("testInput1");
        problem1.setOutput("testOutput1");

        Problem problem2 = new Problem();
        problem2.setTitle("testTitle");
        problem2.setDescription("description2");
        problem2.setInput("testInput2");
        problem2.setOutput("testOutput2");

        long id1 = problemService.createProblem(problem1);
        long id2 = problemService.createProblem(problem2);
        Problem findProblem = problemService.getProblemById(id1);

        assertThat(findProblem.getTitle()).isEqualTo(problem1.getTitle());
        assertThat(id2).isEqualTo(-1);
    }

    @Test
    void 문제조회() {
        Problem problem = new Problem();
        problem.setTitle("testTitle");
        problem.setDescription("description");
        problem.setInput("testInput");
        problem.setOutput("testOutput");

        long id = problemService.createProblem(problem);
        Problem findProblem1 = problemService.getProblemById(id);

        assertThat(findProblem1.getTitle()).isEqualTo(problem.getTitle());

        Problem findProblem2 = problemService.getProblemById(14L);

        assertThat(findProblem2.getId()).isEqualTo(-1);
    }

    @Test
    void 문제수정() {
        Problem problem = new Problem();
        problem.setTitle("testTitle");
        problem.setDescription("description");
        problem.setInput("testInput");
        problem.setOutput("testOutput");

        long id = problemService.createProblem(problem);
        Problem updateProblem = new Problem();
        updateProblem.setId(id);
        updateProblem.setTitle("updateTitle");
        updateProblem.setDescription("updatedescription");
        updateProblem.setInput("updateInput");
        updateProblem.setOutput("updateOutput");

        problemService.updatProblemById(updateProblem);

        Problem findProblem = problemService.getProblemById(id);

        assertThat(findProblem.getTitle()).isEqualTo(updateProblem.getTitle());
    }

    @Test
    void 문제삭제() {
        Problem problem = new Problem();
        problem.setTitle("testTitle");
        problem.setDescription("description");
        problem.setInput("testInput");
        problem.setOutput("testOutput");

        long id = problemService.createProblem(problem);

        Problem findProblem1 = problemService.getProblemById(id);
        assertThat(findProblem1.getTitle()).isEqualTo(problem.getTitle());

        problemService.deletProblemById(id);
        Problem findProblem2 = problemService.getProblemById(id);
        assertThat(findProblem2.getId()).isEqualTo(-1);
    }
}
