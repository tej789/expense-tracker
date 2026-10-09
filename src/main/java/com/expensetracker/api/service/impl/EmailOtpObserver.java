package com.expensetracker.api.service.impl;

import com.expensetracker.api.model.User;
import com.expensetracker.api.service.OtpObserver;
import org.springframework.stereotype.Component;

@Component
public class EmailOtpObserver implements OtpObserver {

    private final JavaMailSenderAdapter javaMailSenderAdapter;

    public EmailOtpObserver(JavaMailSenderAdapter javaMailSenderAdapter){
        this.javaMailSenderAdapter = javaMailSenderAdapter;
    }


    @Override
    public void sendOtp(User user, String otp) {

        javaMailSenderAdapter.sendEmail(
                user.getMail(),
                "Expense Tracker Email Verification",
                "Your Verification Code is: " + otp
        );

    }
}

