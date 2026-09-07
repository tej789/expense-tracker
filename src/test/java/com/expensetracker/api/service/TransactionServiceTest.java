package com.expensetracker.api.service;


import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
     TransactionRepository transactionRepository;

    @Mock
     UserRepository userRepository;

    @Mock
    CurrentUserService currentUserService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void shouldRejectZeroAmount() {

        TransactionRequest request = new TransactionRequest();
        request.setAmount(0);

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

//    @Test
//    void UserNotFound() {
//
//        TransactionRequest request = new TransactionRequest();
//        request.setAmount(100);
//        request.setTransactionDate(LocalDate.now());
//
//        when(currentUserService.getCurrentUser())
//                .thenThrow(new NoSuchElementException("User Not Found"));
//
//        assertThrows(
//                NoSuchElementException.class,
//                () -> transactionService.addTransaction(request)
//        );
//
//    }


    @Test
    void shouldNotDeleteTransactionThatNotExist(){

        User user =new User();
        user.setId(1);
        user.setUsername("tej");

        when(currentUserService.getCurrentUser()).thenReturn(user);

        Transaction transaction = new Transaction();
        transaction.setId(1);

        when(transactionRepository.findByIdAndUserIdAndActiveTrue(transaction.getId(),user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> transactionService.deleteTransaction(transaction.getId())
        );
    }

    @Test
    void shouldNotAllowFutureTransactionDate() {

        TransactionRequest request = new TransactionRequest();

        request.setAmount(100);
        request.setTransactionDate(LocalDate.now().plusDays(1));

        assertThrows(
                IllegalArgumentException.class,
                ()->transactionService.addTransaction(request)
        );

    }

    @Test
    void CannotUpdateTransactionWithoutAuthorizeUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("tej");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tej",null)
        );

        when(userRepository.findByUsername("tej"))
                .thenReturn(Optional.of(user));


        int transactionId =4;

        when(transactionRepository.findByIdAndUserIdAndActiveTrue(
                transactionId,
                user.getId()
        )).thenReturn(Optional.empty());

        TransactionRequest request = new TransactionRequest();


        assertThrows(
                NoSuchElementException.class,
                () -> transactionService.updateTransaction(transactionId,request)
        );
    }
}
