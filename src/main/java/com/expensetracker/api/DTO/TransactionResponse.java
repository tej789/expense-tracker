package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponse {

    private int id;
    private double amount;
    private String description;
    private LocalDate transactionDate;
    private TransactionType type;
    private CategoryType category;

    public TransactionResponse() {
    }
}