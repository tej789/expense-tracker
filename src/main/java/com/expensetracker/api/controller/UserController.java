package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;



    public UserController(UserService userService){
        this.userService = userService;

    }


//  @GetMapping("/users")
//  @PreAuthorize("hasRole('ADMIN')")
//  public ResponseEntity<List<UserResponse>> getAllUsers(){
//        List<UserResponse> users = userService.getAllUsers();
//        return new ResponseEntity<>(users,HttpStatus.OK);
//  }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<UserResponse>> getAllUser(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue =  "1")    int size
  ){
Page<UserResponse> usersPage = userService.getAllUser(page, size);
return new ResponseEntity<>(usersPage, HttpStatus.OK);

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

    @PutMapping("/user/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        userService.updateProfile(request);

        return ResponseEntity.ok(
                new ApiResponse("Profile updated successfully")
        );
    }
}
