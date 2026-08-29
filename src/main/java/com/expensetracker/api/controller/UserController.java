package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.LoginResponse;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.model.User;
import com.expensetracker.api.service.JwtService;
import com.expensetracker.api.service.UserService;
import jakarta.transaction.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    public UserController(UserService userService,JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

@PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        User user = userService.login(request);
    String token = jwtService.generateToken(user.getUsername(), user.getRole());

    return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
  }


}
