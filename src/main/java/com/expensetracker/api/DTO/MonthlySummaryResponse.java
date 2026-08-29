package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlySummaryResponse {

    private CategoryType category;
    private double budget;
    private double spent;
    private double remaining;
    private boolean overBudget;

    public MonthlySummaryResponse(
            CategoryType category,
            double budget,
            double spent
    ) {
        this.category = category;
        this.budget = budget;
        this.spent = spent;
        this.remaining = budget - spent;
        this.overBudget = spent > budget;
    }
}