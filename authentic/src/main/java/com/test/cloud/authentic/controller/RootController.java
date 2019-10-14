package com.test.cloud.authentic.controller;

import com.test.cloud.authentic.dao.UserRepository;
import com.test.cloud.authentic.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class RootController {
    @Resource
    private UserRepository userRepository;
    @GetMapping("")
    public String index() {
        return "this is authentic";
    }
    @PostMapping("user")
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);
    }
    @GetMapping("user")
    public Principal getUser(Principal user) {
        return user;
    }
}
