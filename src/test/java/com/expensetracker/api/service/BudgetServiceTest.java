package com.expensetracker.api.service;

import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    BudgetRepository budgetRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    BudgetService budgetService;

    @Test
    void shouldNotAllowDuplicateBudget() {

        User user = new User();
        user.setId(1);
        user.setUsername("tej");

        BudgetRequest request = new BudgetRequest();
        request.setCategory(CategoryType.Food);
        request.setMonth(Month.MARCH);
        request.setYear(Year.of(2026));
        request.setAmount(5000);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej", null)
        );

        when(userRepository.findByUsername("tej"))
                .thenReturn(Optional.of(user));

        when(budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                1,
                CategoryType.Food,
                Month.MARCH,
                Year.of(2026)
        )).thenReturn(Optional.of(new Budget()));

        assertThrows(
                IllegalArgumentException.class,
                () -> budgetService.setBudget(request)
        );
    }



}