package com.expensetracker.api.service;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.DTO.BudgetResponse;
import com.expensetracker.api.DTO.TotalBudgetResponse;
import com.expensetracker.api.model.CategoryType;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface BudgetService
{

     BudgetResponse setBudget(BudgetRequest request);

     BudgetResponse updateBudget(BudgetRequest request);

     BudgetResponse getBudget(Month month, Year year, CategoryType category);

     void deleteBudget(Month month, Year year, CategoryType category);

     List<BudgetResponse> getAllBudgets(Month month, Year year);

     TotalBudgetResponse getTotalMonthlyBudget(Month month, Year year);

}
