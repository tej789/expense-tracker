package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonthlySummaryResponse {

    private CategoryType category;
    private BigDecimal budget;
    private BigDecimal spent;
    private BigDecimal remaining;
    private boolean overBudget;

    public MonthlySummaryResponse(
            CategoryType category,
            BigDecimal budget,
            BigDecimal spent
    ) {
        this.category = category;
        this.budget = budget;
        this.spent = spent;
        this.remaining = budget.subtract(spent);
        this.overBudget = spent.compareTo(budget)>0;
    }
}