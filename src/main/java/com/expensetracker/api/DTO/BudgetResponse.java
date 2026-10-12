package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

@Data
public class BudgetResponse {

    private CategoryType category;
    private BigDecimal amount;
    private Month month;
    private Year year;

    public BudgetResponse(){}

}
