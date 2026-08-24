package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

@PostMapping("/user")
public ResponseEntity<String> Register(@RequestBody RegisterRequest request){

     String msg = userService.registerUser(request);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
}

@GetMapping("/user")
  public ResponseEntity<String> Login(@RequestBody RegisterRequest request){

        if(userService.login(request)){
            return new ResponseEntity<>("User Login Successful",HttpStatus.OK);
        }

        return new ResponseEntity<>("Enter the Correct Username Or Password",HttpStatus.BAD_REQUEST);
  }
}
