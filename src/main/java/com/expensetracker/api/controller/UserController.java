package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.RegisterRequest;
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

    @PreAuthorize("hasRole('USER')")
@PostMapping("/register")
public ResponseEntity<String> Register(@RequestBody RegisterRequest request){

     String msg = userService.registerUser(request);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
}

@PostMapping("/login")
  public ResponseEntity<String> Login(@RequestBody RegisterRequest request){

        User user = userService.login(request);
    String token = jwtService.generateToken(user.getUsername(), user.getRole());
    return new ResponseEntity<>(token, HttpStatus.OK);
  }


}
