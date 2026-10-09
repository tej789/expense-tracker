package com.expensetracker.api.DTO;

import lombok.Getter;

@Getter
public class ApiResponse {

    private final String message;

    public ApiResponse(String message) {
        this.message = message;
    }

}