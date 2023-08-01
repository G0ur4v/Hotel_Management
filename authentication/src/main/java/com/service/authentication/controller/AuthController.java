package com.service.authentication.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.authentication.entities.Response;
import com.service.authentication.entities.User;
import com.service.authentication.exception.UserErrorException;
import com.service.authentication.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication authenticate;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getAdmin() {
        return "Admin access";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")
    public String getManager() {
        return "Manager Access";
    }

    @PostMapping("/register")
    public Response addUser(@RequestBody User user) {
        System.out.println("--------------------testing ----------------------" + user.toString());
        try {
            authService.saveUser(user);
            return Response.builder()
                    .success(true)
                    .message("User Registered Successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (UserErrorException e) {

            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }

    }

    @PostMapping("/token")
    public Response getToken(@RequestBody User user) {
        System.out.println("--------------------testing ----------------------" + user.getUsername());
        try {
            authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authenticate.isAuthenticated()) {

                String token = authService.generateToken(user.getUsername());
                return Response.builder()
                        .success(true)
                        .message("Token Generated Successfully")
                        .token(token)
                        .code(HttpStatus.SC_OK)
                        .build();
            } else {
                return Response.builder()
                        .success(false)
                        .message("Failure! Token not generated!")
                        .token(null)
                        .code(HttpStatus.SC_BAD_REQUEST)
                        .build();
            }
        } catch (Exception e) {
            return Response.builder()
                    .success(false)
                    .message("Invalid username or password")
                    .token(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

}
