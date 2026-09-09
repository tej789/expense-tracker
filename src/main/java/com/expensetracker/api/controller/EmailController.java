package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.VerifyEmailRequest;
import com.expensetracker.api.Exception.Response;
import com.expensetracker.api.service.AuthService;
import com.expensetracker.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final AuthService authService;

    public EmailController(EmailService emailService,AuthService authService) {
        this.emailService = emailService;
        this.authService = authService;
    }

    @GetMapping("/test-email")
    public String testEmail(@RequestParam String email) {

        emailService.sendEmail(
                email,
                "Expense Tracker",
                "This is a email from Expense Tracker."
        );

        return "Email sent successfully";
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request) {

        authService.verifyEmail(request);

        return ResponseEntity.ok(
                new Response("Success", "Email verified successfully")
        );
    }
}