package com.expensetracker.api.service;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BudgetService
{

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository,UserRepository userRepository){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;

    }

    public String setBudget(BudgetRequest request){



        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Optional<Budget> budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                request.getUserId(), request.getCategory(), request.getMonth(), request.getYear()
        );

if(budget.isPresent()){
    Budget Updated = budget.get();
    Updated.setAmount(request.getAmount());
    budgetRepository.save(Updated);
    return "Budget updated successfully!";
}else{

    Budget newBudget = new Budget();

    newBudget.setAmount(request.getAmount());
    newBudget.setMonth(request.getMonth());
    newBudget.setYear(request.getYear());
    newBudget.setCategory(request.getCategory());
    newBudget.setUser(user);

    budgetRepository.save(newBudget);
    return "Budget created successfully!";
}

    }


}
