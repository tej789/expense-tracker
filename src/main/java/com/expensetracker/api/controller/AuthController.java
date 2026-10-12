package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.model.User;
import com.expensetracker.api.service.AuthService;
import com.expensetracker.api.service.JwtService;
import jakarta.validation.Valid;
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
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        User user = authService.login(request);
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgetPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ){
        authService.forgotPassword(request);

        return  new ResponseEntity<>(new ApiResponse("Password reset Otp sent Successfully"), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> ResetPassword(
            @Valid @RequestBody ResetPasswordRequest request){
                authService.resetPassword(request);

                return new ResponseEntity<>(new ApiResponse("Password Reset Successfully"),HttpStatus.OK);
    }

}
