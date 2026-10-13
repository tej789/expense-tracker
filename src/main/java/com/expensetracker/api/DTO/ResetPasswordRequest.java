package com.expensetracker.api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank
    @Email
    private String mail;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$",
            message = "OTP must contain 6 digits")
    private String resetPasswordCode;


    @NotBlank
    @Size(min=8,message = "Password Must Contain At Least 8 Characters")
    private String newPassword;

}

