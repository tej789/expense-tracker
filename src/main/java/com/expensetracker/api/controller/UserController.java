package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.model.User;
import com.expensetracker.api.service.JwtService;
import com.expensetracker.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    public UserController(UserService userService,JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }


  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
  }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserResponse> getUserById(
          @PathVariable("id") int id
  ){
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
  }


    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable int id) {

        userService.deleteUserById(id);

        return ResponseEntity.ok(
                new MessageResponse("User deleted successfully")
        );
    }
}
