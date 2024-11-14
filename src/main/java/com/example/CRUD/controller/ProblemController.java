package com.example.CRUD.controller;

import com.example.CRUD.model.Example;
import com.example.CRUD.model.Problem;
import com.example.CRUD.service.ExampleService;
import com.example.CRUD.service.ProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.*;

import java.util.List;

@Controller
public class ProblemController {
    
    private final ProblemService problemService;
    private final ExampleService exampleService;

    @Autowired
    public ProblemController(ProblemService problemService, ExampleService exampleService) {
        this.problemService = problemService;
        this.exampleService = exampleService;
    }

    @GetMapping("/problems")
    public String goProblems(Model model) {
        List<Problem> problems = problemService.getAllProblems();
        model.addAttribute("problems", problems);
        return "problems/problemList";
    }

    @GetMapping("/problems/{id}")
    public String goProblemById(@PathVariable long id, Model model) {
        Problem problem = problemService.getProblemById(id);
        model.addAttribute("problem", problem);
        return "problems/problemDetail";
    }
    

    @GetMapping("/problems/create")
    public String goProblemCreate() {
        return "problems/problemCreate";
    }
    
    @PostMapping("/problems/create")
    public String postMethodName(Problem problemForm) {
        Problem problem = new Problem();
        problem.setTitle(problemForm.getTitle());
        problem.setDescription(problemForm.getDescription());
        problem.setInput(problemForm.getInput());
        problem.setOutput(problemForm.getOutput());

        long id = problemService.createProblem(problem);
        if (id == -1) {
            System.out.println("이미 존재");
            return "redirect:/problems/create";
        }
        return "redirect:/problems";
    }

    @PostMapping("/problems/test/{id}")
    public String codeTest(@PathVariable long id, String code, String lang) {
        List<Example> examples = exampleService.getAllExamplesByProblemId(id);

        if (lang.equals("JAVA")) {
            for (Example example : examples) {
                String result = JavaTest(code, example);
                System.out.println(result);
            }
        }

        return "redirect:/problems/" + id;
    }

    static String JavaTest(String code, Example example) {
        String className = "Main";

        Path javaFilePath = Paths.get(className + ".java");
        try {
            Files.write(javaFilePath, code.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error writing Java file";
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "--release", "17", javaFilePath.toString());
        if (result != 0) {
            return "Compilation Error";
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", className);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(example.getInput());
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            if (output.length() > 0) {
                output.deleteCharAt(output.length() - 1);
            }

            process.waitFor();

            // Clean up the generated files
            Files.deleteIfExists(javaFilePath);
            Files.deleteIfExists(Paths.get(className + ".class"));

            System.out.println("=============== 출력");
            System.out.println(output.toString());
            System.out.println("=============== 답");
            System.out.println(example.getOutput());

            if (output.toString().equals(example.getOutput())) return "성공";
            else return "실패";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    
}
