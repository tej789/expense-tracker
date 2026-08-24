package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private int userId;
    private double amount;
    private String description;
    private LocalDate transactionDate;
    private TransactionType type;
    private CategoryType category;
}
