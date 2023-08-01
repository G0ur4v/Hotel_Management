package com.service.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.authentication.entities.User;
import com.service.authentication.exception.UserErrorException;
import com.service.authentication.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User saveUser(User user) throws UserErrorException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return repository.save(user);
        } catch (Exception e) {
            throw new UserErrorException("Username and Email Should be unique");
        }

    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
