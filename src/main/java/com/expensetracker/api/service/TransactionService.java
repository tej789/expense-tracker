package com.expensetracker.api.service;

import com.expensetracker.api.DTO.TotalExpenseResponse;
import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.DTO.TransactionResponse;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface TransactionService {

     TransactionResponse addTransaction(TransactionRequest request);

  List<TransactionResponse>  getTransaction();

     List<TransactionResponse> getTransactionByMonth(Year year, Month month);

     TransactionResponse updateTransaction(int transactionId , TransactionRequest request);

     void deleteTransaction(int transactionId);

     TotalExpenseResponse getTotalMonthlyExpense(Month month, Year year);
}


