package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.service.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

private final TransactionService transactionService;

public TransactionController(TransactionService transactionService){
    this.transactionService = transactionService;
}

@PostMapping("/addTransaction")
public ResponseEntity<String> add(@RequestBody TransactionRequest request){
    String msg = transactionService.addTransaction(request);
    return new ResponseEntity<>(msg, HttpStatus.CREATED);
}

}
