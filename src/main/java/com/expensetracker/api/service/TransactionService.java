package com.expensetracker.api.service;

import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.request.async.SecurityContextCallableProcessingInterceptor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,UserRepository userRepository){
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    public String addTransaction(TransactionRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setUser(user);

        transactionRepository.save(transaction);

        return "Transaction recorded successfully!";
    }

 public List<Transaction>  getTransaction(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user =userRepository.findByUsername(username)
                .orElseThrow(()-> new NoSuchElementException("No user Found"));

        int Id = user.getId();

        return transactionRepository.findByUserId(Id);
 }

 public Transaction
}
