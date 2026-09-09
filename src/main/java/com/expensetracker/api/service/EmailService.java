package com.expensetracker.api.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}