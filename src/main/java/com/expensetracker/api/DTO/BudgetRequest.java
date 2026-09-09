package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Month;
import java.time.Year;

@Data
public class BudgetRequest {

    @NotNull(message = "Category is required")
    private CategoryType category;

    private double amount;

    @NotNull(message = "Month is required")
    private Month month;

    @NotNull(message = "Year is required")
    private Year year;

    public BudgetRequest() {
    }
}
