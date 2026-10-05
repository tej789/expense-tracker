package com.expensetracker.api.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}


/*
@Component
public class JavaMailSenderAdapter implements EmailService {

    private final JavaMailSender mailSender;

    public JavaMailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}


 */