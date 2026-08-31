package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.TotalExpenseResponse;
import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.DTO.TransactionResponse;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;
import com.expensetracker.api.model.Transaction;



@RestController
public class TransactionController {

private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository){
    this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }


@PostMapping("/transaction")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<TransactionResponse> add(@RequestBody TransactionRequest request){
    TransactionResponse msg = transactionService.addTransaction(request);
    return new ResponseEntity<>(msg, HttpStatus.CREATED);
}


@GetMapping("/transaction")
@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TransactionResponse>> getTransaction(){
        List<TransactionResponse> list = transactionService.getTransaction();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/transaction/month")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TransactionResponse>> getTransactionByMonth(
            @RequestParam Year year,
            @RequestParam Month month
            ){

        List<TransactionResponse> list = transactionService.getTransactionByMonth(year,month);
      return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/transaction/total")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TotalExpenseResponse> getTotalMonthlyExpense(
            @RequestParam  Month month, @RequestParam Year year
    ){
        TotalExpenseResponse response = transactionService.getTotalMonthlyExpense(month,year);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

  @PutMapping("/transaction/{transactionId}")
  @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable("transactionId") int transactionId
           ,@RequestBody TransactionRequest request
    ){

    TransactionResponse n =  transactionService.updateTransaction(transactionId,request);

    return new ResponseEntity<>(n,HttpStatus.OK);

    }


    @DeleteMapping("/transaction/{transactionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable("transactionId") int transactionId){

        transactionService.deleteTransaction(transactionId);
    return new ResponseEntity<>("Transaction Deleted Successfully",HttpStatus.OK);
    }

}
