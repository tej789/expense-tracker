package com.expensetracker.api.service;
import com.expensetracker.api.model.CategoryType;

import java.time.Month;
import java.time.Year;

public interface BudgetAlertService {

    void checkBudgetAlert(
            Month month,
            Year year,
            CategoryType category
    );
}
