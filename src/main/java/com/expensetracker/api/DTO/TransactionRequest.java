package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {

    @Positive(message = "Transaction amount must be greater than 0")
    private BigDecimal amount;

    private String description;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Category is required")
    private CategoryType category;

    public TransactionRequest() {
    }
}