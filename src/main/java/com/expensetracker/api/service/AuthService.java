package com.expensetracker.api.service;


import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.model.User;

public interface AuthService {

     RegisterResponse registerUser(RegisterRequest request);

     User login(LoginRequest request);

}
