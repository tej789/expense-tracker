package com.expensetracker.api.service;


import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void TransactionAmountZero(){

        User user = new User();
        user.setId(1);
        user.setUsername("tej");

        TransactionRequest request = new TransactionRequest();
        request.setAmount(0);


        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej", null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.addTransaction(request)
        );


    }

    @Test
    void TransactionAmountNegative(){

        User user = new User();
        user.setId(1);
        user.setUsername("tej");

        TransactionRequest request = new TransactionRequest();
        request.setAmount(-5000);


        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej", null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.addTransaction(request)
        );


    }

    @Test
    void UserNotFound() {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej", null)
        );

        TransactionRequest request = new TransactionRequest();
        request.setAmount(100);

        when(userRepository.findByUsername("tej"))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> transactionService.addTransaction(request)
        );

    }
}
