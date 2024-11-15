package com.example.CRUD.controller;

import com.example.CRUD.model.User;
import com.example.CRUD.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public String goUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }

    @GetMapping("/users/create")
    public String goCreateUser() {
        return "users/createUser";
    }
    
    @GetMapping("/users/login")
    public String goUserLogin() {
        return "users/userLogin";
    }
    
    @PostMapping("/users/create")
    public String createUser(User userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setUserId(userForm.getUserId());
        user.setUserPw(userForm.getUserPw());

        long id = userService.join(user);
        if (id == -1) {
            System.out.println("이미 존재");
            return "redirect:/users/create";
        }
        return "redirect:/users/login";
    }

    @PostMapping("/users/login")
    public String userLogin(User userForm, Model model, HttpSession httpSession) {
        User user = userService.getUserByUserId(userForm.getUserId());
        if (user.getId() != -1) {
            if (user.getUserPw().equals(userForm.getUserPw())) {
                httpSession.setAttribute("user", user);
                return "redirect:/";
            }
        }
        return "redirect:/users/login";
    }

    @PutMapping("/users/update")
    public String updateUser(User user) {
        userService.updateUserById(user);
        return "redirect:/users";
    }
    

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
    
}
