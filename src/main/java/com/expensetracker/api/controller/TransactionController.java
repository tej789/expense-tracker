package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.expensetracker.api.model.*;
import java.util.List;
import com.expensetracker.api.model.Transaction;



@RestController
public class TransactionController {

private final TransactionService transactionService;

public TransactionController(TransactionService transactionService){
    this.transactionService = transactionService;
}


@PostMapping("/addTransaction")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<String> add(@RequestBody TransactionRequest request){
    String msg = transactionService.addTransaction(request);
    return new ResponseEntity<>(msg, HttpStatus.CREATED);
}


@GetMapping("/getTransaction")
@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Transaction>> getTransaction(){
        List<Transaction> list = transactionService.getTransaction();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
