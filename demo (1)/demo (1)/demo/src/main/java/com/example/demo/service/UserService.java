package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        //유저객체가 비었는지 아닌지 판별
        if(userEntity == null || userEntity.getUsername()==null){
            throw new RuntimeException("invalid argument");
        }

        //이미 존재하는 유저인지 아닌지 판별
        final String username = userEntity.getUsername();

        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        //유저생성
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByUsername(username);

        if(originalUser != null &&
        encoder.matches(password,
                originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }
}
