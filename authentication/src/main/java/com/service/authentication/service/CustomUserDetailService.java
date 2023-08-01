package com.service.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.service.authentication.config.CustomerUserDetail;
import com.service.authentication.entities.User;
import com.service.authentication.repository.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);

        return user.map(CustomerUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }

}
