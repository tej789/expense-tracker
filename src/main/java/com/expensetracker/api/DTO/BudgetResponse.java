package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Data;

import java.time.Month;
import java.time.Year;

@Data
public class BudgetResponse {

    private CategoryType category;
    private double amount;
    private Month month;
    private Year year;

    public BudgetResponse(){}

}
