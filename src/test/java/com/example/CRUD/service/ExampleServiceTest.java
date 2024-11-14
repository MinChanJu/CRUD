package com.example.CRUD.service;

import com.example.CRUD.model.Example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ExampleServiceTest {
    
    @Autowired ExampleService exampleService;

    @Test
    void 예제추가() {
        Example example = new Example();
        example.setProblemId(24);
        example.setInput("testInput");
        example.setOutput("testOutput");

        long id = exampleService.createExample(example);
        Example findExample = exampleService.getExampleById(id);

        assertThat(findExample.getInput()).isEqualTo(example.getInput());
    }

    @Test
    void 예제조회() {
        long problemId1 = 24;
        long problemId2 = 20;
        Example example1 = new Example();
        example1.setProblemId(problemId1);
        example1.setInput("testInput1");
        example1.setOutput("testOutput1");

        Example example2 = new Example();
        example2.setProblemId(problemId1);
        example2.setInput("testInput2");
        example2.setOutput("testOutput2");

        Example example3 = new Example();
        example3.setProblemId(problemId1);
        example3.setInput("testInput3");
        example3.setOutput("testOutput3");

        Example example4 = new Example();
        example4.setProblemId(problemId2);
        example4.setInput("testInput4");
        example4.setOutput("testOutput4");

        Example example5 = new Example();
        example5.setProblemId(problemId2);
        example5.setInput("testInput5");
        example5.setOutput("testOutput5");

        exampleService.createExample(example1);
        exampleService.createExample(example2);
        exampleService.createExample(example3);
        exampleService.createExample(example4);
        long id = exampleService.createExample(example5);

        List<Example> examples = exampleService.getAllExamplesByProblemId(problemId1);
        assertThat(examples.size()).isEqualTo(3);

        Example findExample = exampleService.getExampleById(id);
        assertThat(findExample.getInput()).isEqualTo(example5.getInput());
    }

    @Test
    void 예제수정() {

    }

    @Test
    void 예제삭제() {
        long problemId1 = 24;
        long problemId2 = 20;
        Example example1 = new Example();
        example1.setProblemId(problemId1);
        example1.setInput("testInput1");
        example1.setOutput("testOutput1");

        Example example2 = new Example();
        example2.setProblemId(problemId1);
        example2.setInput("testInput2");
        example2.setOutput("testOutput2");

        Example example3 = new Example();
        example3.setProblemId(problemId1);
        example3.setInput("testInput3");
        example3.setOutput("testOutput3");

        Example example4 = new Example();
        example4.setProblemId(problemId2);
        example4.setInput("testInput4");
        example4.setOutput("testOutput4");

        Example example5 = new Example();
        example5.setProblemId(problemId2);
        example5.setInput("testInput5");
        example5.setOutput("testOutput5");

        exampleService.createExample(example1);
        exampleService.createExample(example2);
        exampleService.createExample(example3);
        exampleService.createExample(example4);
        long id = exampleService.createExample(example5);

        Example findExample1 = exampleService.getExampleById(id);
        assertThat(findExample1.getId()).isEqualTo(example5.getId());

        exampleService.deleteExampleById(id);
        Example findExample2 = exampleService.getExampleById(id);
        assertThat(findExample2.getId()).isEqualTo(-1);

        List<Example> examples1 = exampleService.getAllExamplesByProblemId(problemId1);
        assertThat(examples1.size()).isEqualTo(3);

        exampleService.deleteExampleByProblemId(problemId1);
        List<Example> examples2 = exampleService.getAllExamplesByProblemId(problemId1);
        assertThat(examples2.size()).isEqualTo(0);
    }
}
