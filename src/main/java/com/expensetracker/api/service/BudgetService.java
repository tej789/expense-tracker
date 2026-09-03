package com.expensetracker.api.service;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.DTO.BudgetResponse;
import com.expensetracker.api.DTO.TotalBudgetResponse;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BudgetService
{

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository,UserRepository userRepository){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;

    }
    public BudgetResponse setBudget(BudgetRequest request) {

        if(request.getAmount()<1){
            throw new  IllegalArgumentException("Amount should not be zero or negative");
        }

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

        BudgetResponse response = new BudgetResponse();

        response.setCategory(newBudget.getCategory());
        response.setAmount(newBudget.getAmount());
        response.setMonth(newBudget.getMonth());
        response.setYear(newBudget.getYear());

        return response;

    }


   public BudgetResponse getBudget(java.time.Month month, java.time.Year year, CategoryType category){

       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new NoSuchElementException("User not found"));


       Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
               user.getId(), category, month, year
       ).orElseThrow(() -> new NoSuchElementException("No budget set for this category in the specified month and year."));

       BudgetResponse response = new BudgetResponse();
       response.setAmount(budget.getAmount());
       response.setMonth(budget.getMonth());
       response.setYear(budget.getYear());
       response.setCategory(budget.getCategory());

       return response;
   }


   public List<BudgetResponse> getAllBudgets(Month month,Year year){

       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new NoSuchElementException("User not found"));

       List<Budget> budgets = budgetRepository.findByUserIdAndMonthAndYear(user.getId(),month,year);

       List<BudgetResponse> List = new ArrayList<>();
       for (Budget budget : budgets) {

           BudgetResponse response = new BudgetResponse();
           response.setAmount(budget.getAmount());
           response.setMonth(budget.getMonth());
           response.setYear(budget.getYear());
           response.setCategory(budget.getCategory());

           List.add(response);
       }
       return List;
    }

    public BudgetResponse updateBudget(BudgetRequest request){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        Budget budget = (Budget) budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                user.getId(), request.getCategory(), request.getMonth(), request.getYear()
        ).orElseThrow(() -> new NoSuchElementException("Budget not found for this category, month, and year."));

        budget.setAmount(request.getAmount());

        budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setCategory(budget.getCategory());
        response.setAmount(budget.getAmount());
        response.setMonth(budget.getMonth());
        response.setYear(budget.getYear());

        return response;
    }


    public void deleteBudget(Month month, Year year, CategoryType category) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                user.getId(), category, month, year
        ).orElseThrow(() -> new NoSuchElementException("No budget found for this category in the specified month and year."));

        budgetRepository.delete(budget);
    }



    public TotalBudgetResponse getTotalMonthlyBudget(Month month, Year year){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        int userId =user.getId();
        double totalBudget = 0;

        List<Budget> budgets = new ArrayList<>();

        budgets = budgetRepository.findByUserIdAndMonthAndYear(
                userId,
                month,year
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
