package com.example.CRUD.service;

import com.example.CRUD.model.User;
import com.example.CRUD.repository.JpaUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final JpaUserRepository userRepository;
    private User elseUser = new User();

    @Autowired
    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
        elseUser.setId(-1);
    }

    public long join(User user) {
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());
        if (findUser.isEmpty()) {
            userRepository.save(user);
            return user.getId();
        }
        return -1;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseGet(() -> elseUser);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseGet(() -> elseUser);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseGet(() -> elseUser);
    }

    public User getUserByUserPw(String userPw) {
        return userRepository.findByUserPw(userPw).orElseGet(() -> elseUser);
    }

    public User updateUserById(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseGet(() -> elseUser);
        user.setName(updateUser.getName());
        user.setUserId(updateUser.getUserId());
        user.setUserPw(updateUser.getUserPw());
        userRepository.save(user);
        return user;
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
    
}
