package com.expensetracker.api.service;

import com.expensetracker.api.DTO.TotalExpenseResponse;
import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.DTO.TransactionResponse;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.TransactionType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface TransactionService {

     TransactionResponse addTransaction(TransactionRequest request);

    Page<TransactionResponse> getTransactions(int page, int size);

    List<TransactionResponse> getTransactionByMonth(Year year, Month month);

     TransactionResponse updateTransaction(int transactionId , TransactionRequest request);

     void deleteTransaction(int transactionId);

     TotalExpenseResponse getTotalMonthlyExpense(Month month, Year year);

     Page<TransactionResponse> filterTransactions(
             LocalDate startDate,
             LocalDate endDate,
             CategoryType category,
             TransactionType type,
             BigDecimal minAmount,
             BigDecimal maxAmount,
             int page,
             int size
     );

     byte[] exportMonthlyTransactions(Month month, Year year);

    String escapeCsv(String value);
}


