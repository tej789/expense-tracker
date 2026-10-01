package com.expensetracker.api.service;

import com.expensetracker.api.model.User;

public interface OtpObserver {
    void sendOtp(User user,String otp);
}
