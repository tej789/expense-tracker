package com.expensetracker.api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VerifyEmailRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String mail;

    @NotBlank(message = "Verification code is required")
    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "Verification code must contain 6 digits"
    )
    private String verificationCode;

    public VerifyEmailRequest() {
    }
}