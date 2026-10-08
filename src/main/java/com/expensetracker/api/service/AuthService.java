package com.expensetracker.api.service;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.model.User;

public interface AuthService {

     RegisterResponse registerUser(RegisterRequest request);

     User login(LoginRequest request);

     void verifyEmail(VerifyEmailRequest request);

     void forgotPassword(ForgotPasswordRequest request);

}
