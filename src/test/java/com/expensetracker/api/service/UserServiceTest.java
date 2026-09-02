package com.expensetracker.api.service;

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

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    UserService userService;
    @Test
    void shouldDeleteUserWithTransactionsAndBudgets() {

        User admin = new User();
        admin.setId(1);
        admin.setUsername("admin");

        User user = new User();
        user.setId(2);
        user.setUsername("user1");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin", null)
        );

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(admin));

        when(userRepository.findById(2))
                .thenReturn(Optional.of(user));

        userService.deleteUserById(2);

        verify(transactionRepository).deleteByUserId(2);
        verify(budgetRepository).deleteByUserId(2);
        verify(userRepository).delete(user);
    }
}
