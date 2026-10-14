package com.expensetracker.api.repository;

import com.expensetracker.api.model.BudgetAlert;
import com.expensetracker.api.model.BudgetAlertType;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;

@Repository
public interface BudgetAlertRepository extends JpaRepository<BudgetAlert,Integer> {

    boolean existsByUserAndCategoryAndMonthAndYearAndAlertType(
            User user,
            CategoryType category,
            Month month,
            Year year,
            BudgetAlertType alertType
    );
}
