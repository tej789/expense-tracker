package com.expensetracker.api.service.Strategy;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CategoryExpenseStrategy implements ExpenseCalculationStrategy {

    @Override
    public double calculate(
            List<Transaction> transactions,
            CategoryType category) {

        double spent = 0;

        for (Transaction transaction : transactions) {

            if (transaction.getCategory() == category) {
                spent += transaction.getAmount();
            }
        }

        return spent;
    }
}

