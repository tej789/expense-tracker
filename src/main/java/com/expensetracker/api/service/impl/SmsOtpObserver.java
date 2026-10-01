package com.expensetracker.api.service.impl;

import com.expensetracker.api.model.User;
import com.expensetracker.api.service.OtpObserver;
import com.expensetracker.api.service.SmsService;
import org.springframework.stereotype.Component;

@Component
public class SmsOtpObserver implements OtpObserver {


    private final SmsService smsService;


    public SmsOtpObserver(SmsService smsService){
        this.smsService = smsService;
    }
    @Override
    public void sendOtp(User user, String otp) {

        smsService.sendSms(
                user.getPhone(),
                "Your Otp Verification Code is : "+otp
        );

    }
}
