package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.MessageResponse;
import com.expensetracker.api.DTO.TotalExpenseResponse;
import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.DTO.TransactionResponse;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import com.expensetracker.api.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;




@RestController
public class TransactionController {

private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
    this.transactionService = transactionService;
    }


@PostMapping("/transaction")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<TransactionResponse> add(@RequestBody TransactionRequest request){
    TransactionResponse msg = transactionService.addTransaction(request);
    return new ResponseEntity<>(msg, HttpStatus.CREATED);
}



    @GetMapping("/transaction")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<TransactionResponse> list = transactionService.getTransactions(page,size);
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
    public ResponseEntity<MessageResponse> deleteTransaction(
            @PathVariable("transactionId") int transactionId
    ) {

        transactionService.deleteTransaction(transactionId);

        return ResponseEntity.ok(
                new MessageResponse("Transaction Deleted Successfully")
        );
    }

    @GetMapping("/transaction/filter")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TransactionResponse>> filterTransactions(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) CategoryType category,
            @RequestParam(required = false)TransactionType type,
            @RequestParam(required = false)BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            ){
        return ResponseEntity.ok(
                transactionService.filterTransactions(
                        startDate,endDate,category,type,
                        minAmount,maxAmount,page,size
                )
        );
    }

    @GetMapping("/transaction/export/monthly")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<byte[]> exportMonthlyTransactions(
            @RequestParam Month month,
            @RequestParam Year year
    ){
        byte[] csv = transactionService.exportMonthlyTransactions(
                month,year
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"transactions-"
                                + month.toString().toLowerCase()
                                + "-" + year + ".csv\""
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(csv.length)
                .body(csv);
    }






}
//