package com.test.cloud.authentic.service;

import com.test.cloud.authentic.dao.UserRepository;
import com.test.cloud.authentic.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty()) {
            throw new RuntimeException("username is null");
        }
        Optional<User> option = userRepository.findByUsername(username);
        if (option.isPresent()) {
            return option.get();
        } else {
            throw new RuntimeException(String.format("user [%s] not exist", username));
        }
    }
}
