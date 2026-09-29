package com.expensetracker.api.service.Strategy;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;

import java.util.List;

public interface ExpenseCalculationStrategy {

    double calculate(List<Transaction> transactions, CategoryType category);
}