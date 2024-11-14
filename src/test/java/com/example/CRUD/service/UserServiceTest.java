package com.example.CRUD.service;

import com.example.CRUD.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {
    
    @Autowired UserService userService;

    @Test
    void 회원가입() {
        User user1 = new User();
        user1.setName("test1");
        user1.setUserId("test1Id");
        user1.setUserPw("test1Pw");

        User user2 = new User();
        user2.setName("test2");
        user2.setUserId("test1Id");
        user2.setUserPw("tes21Pw");

        long id1 = userService.join(user1);
        long id2 = userService.join(user2);
        User findUser = userService.getUserById(id1);
        
        assertThat(findUser.getName()).isEqualTo(user1.getName());
        assertThat(id2).isEqualTo(-1);
    }

    @Test
    void 회원조회() {
        User user = new User();
        user.setName("test1");
        user.setUserId("test1Id");
        user.setUserPw("test1Pw");

        long id = userService.join(user);
        User findUser = userService.getUserById(id);
        assertThat(findUser.getName()).isEqualTo(user.getName());

        User findUser2 = userService.getUserById(14L);
        assertThat(findUser2.getId()).isEqualTo(-1);

    }

    @Test
    void 회원수정() {
        User user = new User();
        user.setName("test1");
        user.setUserId("test1Id");
        user.setUserPw("test1Pw");

        long id = userService.join(user);

        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setName("update1");
        updateUser.setUserId("update1Id");
        updateUser.setUserPw("update1Pw");

        userService.updateUserById(updateUser);

        User findUser = userService.getUserById(id);

        assertThat(findUser.getName()).isEqualTo(updateUser.getName());
    }

    @Test
    void 회원삭제() {
        User user = new User();
        user.setName("test1");
        user.setUserId("test1Id");
        user.setUserPw("test1Pw");

        long id = userService.join(user);

        User findUser1 = userService.getUserById(id);
        assertThat(findUser1.getName()).isEqualTo(user.getName());

        userService.deleteUserById(id);
        User findUser2 = userService.getUserById(id);
        assertThat(findUser2.getId()).isEqualTo(-1);
    }

}
