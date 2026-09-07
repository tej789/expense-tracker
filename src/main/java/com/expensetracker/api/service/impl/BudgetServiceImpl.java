package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.DTO.BudgetResponse;
import com.expensetracker.api.DTO.TotalBudgetResponse;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.service.BudgetService;
import com.expensetracker.api.service.CurrentUserService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BudgetServiceImpl  implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final CurrentUserService currentUserService;

    public BudgetServiceImpl(BudgetRepository budgetRepository, CurrentUserService currentUserService) {
        this.budgetRepository = budgetRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public BudgetResponse setBudget(BudgetRequest request) {
        if (request.getAmount() < 1) {
            throw new IllegalArgumentException("Amount should not be zero or negative");
        }

        User user = currentUserService.getCurrentUser();

        boolean exists = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYearAndActiveTrue(
                        user.getId(),
                        request.getCategory(),
                        request.getMonth(),
                        request.getYear()
                )
                .isPresent();

        if (exists) {
            throw new IllegalArgumentException("Budget already exists for this category in the specified month and year.");
        }


        Budget newBudget = new Budget();
        newBudget.setUser(user);
        newBudget.setMonth(request.getMonth());
        newBudget.setYear(request.getYear());
        newBudget.setCategory(request.getCategory());
        newBudget.setAmount(request.getAmount());


        budgetRepository.save(newBudget);

        BudgetResponse response = new BudgetResponse();

        response.setCategory(newBudget.getCategory());
        response.setAmount(newBudget.getAmount());
        response.setMonth(newBudget.getMonth());
        response.setYear(newBudget.getYear());

        return response;

    }

    @Override
    public BudgetResponse updateBudget(BudgetRequest request){

        User user = currentUserService.getCurrentUser();

        Budget budget = budgetRepository
                .findByUserIdAndCategoryAndMonthAndYearAndActiveTrue(
                        user.getId(),
                        request.getCategory(),
                        request.getMonth(),
                        request.getYear()
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Budget not found for this category, month, and year."));
        budget.setAmount(request.getAmount());

        budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setCategory(budget.getCategory());
        response.setAmount(budget.getAmount());
        response.setMonth(budget.getMonth());
        response.setYear(budget.getYear());

        return response;
    }

    @Override
    public BudgetResponse getBudget(
            Month month,
            Year year,
            CategoryType category) {

        User user = currentUserService.getCurrentUser();

        Optional<Budget> budget =
                budgetRepository.findByUserIdAndCategoryAndMonthAndYearAndActiveTrue(
                        user.getId(), category, month, year
                );

        BudgetResponse response = new BudgetResponse();

        response.setCategory(category);
        response.setMonth(month);
        response.setYear(year);

        if (budget.isPresent()) {
            response.setAmount(budget.get().getAmount());
        } else {
            response.setAmount(0);
        }

        return response;
    }
    @Override
    public void deleteBudget(Month month, Year year, CategoryType category) {
        User user = currentUserService.getCurrentUser();

        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYearAndActiveTrue(
                user.getId(), category, month, year
        ).orElseThrow(() -> new NoSuchElementException("No budget found for this category in the specified month and year."));

        budget.setActive(false);
        budgetRepository.save(budget);
    }

        @Override
public List<BudgetResponse> getAllBudgets(Month month, Year year) {

    User user = currentUserService.getCurrentUser();

            List<Budget> budgets =
                    budgetRepository.findByUserIdAndMonthAndYearAndActiveTrue(
                            user.getId(), month, year
                    );

    List<BudgetResponse> responses = new ArrayList<>();

    for (CategoryType category : CategoryType.values()) {

        BudgetResponse response = new BudgetResponse();

        response.setCategory(category);
        response.setMonth(month);
        response.setYear(year);

        double amount = 0;

        for (Budget budget : budgets) {
            if (budget.getCategory() == category) {
                amount = budget.getAmount();
                break;
            }
        }

        response.setAmount(amount);

        responses.add(response);
    }

    return responses;
}


    @Override
    public TotalBudgetResponse getTotalMonthlyBudget(Month month, Year year){
        User user = currentUserService.getCurrentUser();

        int userId =user.getId();
        double totalBudget = 0;

        List<Budget> budgets = new ArrayList<>();

         budgets = budgetRepository.findByUserIdAndMonthAndYearAndActiveTrue(
                        userId,
                        month,
                        year
                );

        for(Budget budget : budgets){

             totalBudget += budget.getAmount();
        }
     TotalBudgetResponse response = new TotalBudgetResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotalBudget(totalBudget);


        return response;
    }

}

