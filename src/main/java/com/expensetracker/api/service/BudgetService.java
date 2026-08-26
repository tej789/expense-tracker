package com.expensetracker.api.service;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public BudgetRequest setBudget(BudgetRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        boolean exists = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                user.getId(), request.getCategory(), request.getMonth(), request.getYear()
        ).isPresent();

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

        request.setUserId(user.getId());
        return request;
    }


   public BudgetRequest getBudget(int month, int year, CategoryType category){

       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new NoSuchElementException("User not found"));


       Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
               user.getId(), category, month, year
       ).orElseThrow(() -> new NoSuchElementException("No budget set for this category in the specified month and year."));

       BudgetRequest response = new BudgetRequest();
       response.setUserId(user.getId());
       response.setAmount(budget.getAmount());
       response.setMonth(budget.getMonth());
       response.setYear(budget.getYear());
       response.setCategory(budget.getCategory());

       return response;
   }


   public List<BudgetRequest> getAllBudgets(int month,int year){

       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new NoSuchElementException("User not found"));

       List<Budget> budgets = budgetRepository.findByUserIdAndMonthAndYear(user.getId(),month,year);

       List<BudgetRequest> dtoList = new ArrayList<>();
       for (Budget budget : budgets) {

           BudgetRequest dto = new BudgetRequest();
           dto.setUserId(user.getId());
           dto.setAmount(budget.getAmount());
           dto.setMonth(budget.getMonth());
           dto.setYear(budget.getYear());
           dto.setCategory(budget.getCategory());

           dtoList.add(dto);
       }
       return dtoList;
    }

    public BudgetRequest updateBudget(BudgetRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                user.getId(), request.getCategory(), request.getMonth(), request.getYear()
        ).orElseThrow(() -> new NoSuchElementException("Budget not found for this category, month, and year."));

        budget.setAmount(request.getAmount());

        budgetRepository.save(budget);
        request.setUserId(user.getId());
        return request;
    }


    public void deleteBudget(int month, int year,CategoryType category){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));



    }

}
