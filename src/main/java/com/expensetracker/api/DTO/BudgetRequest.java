package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Data;

@Data
public class BudgetRequest {
    private int userId;
    private CategoryType category;
    private double amount;
    private int month;
    private int year;

    public BudgetRequest(){}
}
