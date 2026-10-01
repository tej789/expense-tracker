package com.expensetracker.api.service.impl;

import com.expensetracker.api.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    private final String accountSid = "YOUR_ACCOUNT_SID";
    private final String authToken = "YOUR_AUTH_TOKEN";
    private final String twilioPhoneNumber = "YOUR_TWILIO_PHONE_NUMBER";

    @Override
    public void sendSms(String phone, String message) {

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}