package com.expensetracker.api.service;

import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BudgetRepository budgetRepository;

    @Mock
    CurrentUserService currentUserService;

    @InjectMocks
    UserServiceImpl userService;

//
//    @Test
//    void shouldDeleteUserWithTransactionsAndBudgets() {
//        User admin = new User();
//        admin.setId(1);
//        admin.setUsername("admin");
//
//        User user = new User();
//        user.setId(2);
//        user.setUsername("user1");
//
//        when(currentUserService.getCurrentUser())
//                .thenReturn(admin);
//
//        when(userRepository.findById(2))
//                .thenReturn(Optional.of(user));
//
//        when(transactionRepository.findByUserIdAndActiveTrue(2))
//                .thenReturn(List.of());
//
//        when(budgetRepository.findByUserIdAndActiveTrue(2))
//                .thenReturn(List.of());
//
//        userService.deleteUserById(2);
//
//        verify(transactionRepository).findByUserIdAndActiveTrue(2);
//        verify(transactionRepository).saveAll(List.of());
//
//        verify(budgetRepository).findByUserIdAndActiveTrue(2);
//        verify(budgetRepository).saveAll(List.of());
//
//        verify(userRepository).delete(user);
//    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        User admin = new User();
        admin.setId(1);
        admin.setUsername("admin");

        when(currentUserService.getCurrentUser())
                .thenReturn(admin);

        when(userRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> userService.deleteUserById(2)
        );

        verify(transactionRepository, never()).deleteByUserId(anyInt());
        verify(budgetRepository, never()).deleteByUserId(anyInt());
        verify(userRepository, never()).delete(any(User.class));
    }
    }

