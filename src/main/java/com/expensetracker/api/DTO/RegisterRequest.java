package com.expensetracker.api.DTO;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String mail;
    private String phone;
    private String password;
}