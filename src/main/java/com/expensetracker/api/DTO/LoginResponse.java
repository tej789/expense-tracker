package com.expensetracker.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String token;


    public LoginResponse(String token) {
        this.token = token;
    }
}
