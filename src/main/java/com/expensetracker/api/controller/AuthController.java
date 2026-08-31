package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.LoginResponse;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.model.User;
import com.expensetracker.api.service.AuthService;
import com.expensetracker.api.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;



    public AuthController(AuthService authService,JwtService jwtService){

        this.authService = authService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        User user = authService.login(request);
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }


}
