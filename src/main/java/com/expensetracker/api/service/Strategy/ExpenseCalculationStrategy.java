package com.expensetracker.api.service.Strategy;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseCalculationStrategy {

    BigDecimal calculate(List<Transaction> transactions, CategoryType category);
}