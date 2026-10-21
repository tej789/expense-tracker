package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.PaymentMethod;
import com.expensetracker.api.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponse {

    private int id;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
    private TransactionType type;
    private CategoryType category;
    private PaymentMethod paymentMethod;

    public TransactionResponse() {}
}