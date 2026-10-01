package com.expensetracker.api.service;

public interface SmsService {

    void sendSms(String phone, String message);
}

