package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private int userId;
    private double amount;
    private String description;
    private LocalDate transactionDate;
    private TransactionType type;
    private CategoryType category;

    public TransactionRequest() {
    }

    public TransactionRequest(Transaction x) {
        this.userId = x.getUser().getId();
        this.amount = x.getAmount();
        this.description = x.getDescription();
        this.transactionDate = x.getTransactionDate();
        this.type = x.getType();
        this.category = x.getCategory();
    }
}
