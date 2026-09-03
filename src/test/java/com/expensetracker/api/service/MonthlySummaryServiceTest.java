package com.expensetracker.api.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonthlySummaryServiceTest {

    @Mock
    private  BudgetRepository budgetRepository;

    @Mock
    private  TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MonthlySummaryService monthlySummaryService;

    @Test
    void CategoryBudgetExpenseWithinOrOver(){
        User user = new User();
        user.setId(1);
        user.setUsername("tej");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej",null)
        );

        when(userRepository.findByUsername("tej"))
                .thenReturn(Optional.of(user));


        Budget budget =new Budget();
        budget.setMonth(Month.AUGUST);
        budget.setCategory(CategoryType.Food);
        budget.setYear(Year.of(2026));
        budget.setAmount(8000);

        when(budgetRepository.findByUserIdAndMonthAndYearAndCategory(
                user.getId(),
                budget.getMonth(),
                budget.getYear(),
                budget.getCategory()
        )).thenReturn(budget);

        Transaction transaction = new Transaction();
        transaction.setAmount(8200);
        transaction.setCategory(CategoryType.Food);

     List<Transaction> transactions = new ArrayList<>();
     transactions.add(transaction);

     when(transactionRepository.findByUserIdAndTransactionDateBetween(
             user.getId(),
             LocalDate.of(2026,Month.AUGUST,1),
             LocalDate.of(2026,Month.AUGUST,31)
     )).thenReturn(transactions);


        MonthlySummaryResponse response = monthlySummaryService.getSummaryOfCategory(
                        Month.AUGUST,
                        Year.of(2026),
                        CategoryType.Food
                );

        assertTrue(response.isOverBudget());

    }


}
