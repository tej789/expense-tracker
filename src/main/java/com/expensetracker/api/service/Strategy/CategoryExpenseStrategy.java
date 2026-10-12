package com.expensetracker.api.service.Strategy;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class CategoryExpenseStrategy implements ExpenseCalculationStrategy {

    @Override
    public BigDecimal calculate(List<Transaction> transactions, CategoryType category) {

        BigDecimal spent = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            if (transaction.getCategory() == category) {
                spent = spent.add(transaction.getAmount());
            }
        }

        return spent;
    }
}

