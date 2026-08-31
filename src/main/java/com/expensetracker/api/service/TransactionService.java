package com.expensetracker.api.service;

import com.expensetracker.api.DTO.TotalExpenseResponse;
import com.expensetracker.api.DTO.TransactionRequest;
import com.expensetracker.api.DTO.TransactionResponse;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.request.async.SecurityContextCallableProcessingInterceptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,UserRepository userRepository){
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    public TransactionResponse addTransaction(TransactionRequest request) {
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setUser(user);



        transactionRepository.save(transaction);

        TransactionResponse res = new TransactionResponse();

        res.setId(transaction.getId());
        res.setAmount(transaction.getAmount());
        res.setDescription(transaction.getDescription());
        res.setTransactionDate(transaction.getTransactionDate());
        res.setType(transaction.getType());
        res.setCategory(transaction.getCategory());
        return res;

    }

 public List<TransactionResponse>  getTransaction(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user =userRepository.findByUsername(username)
                .orElseThrow(()-> new NoSuchElementException("No user Found"));

        int id = user.getId();
     List<Transaction> transactions = transactionRepository.findByUserId(id);
     List<TransactionResponse> responses = new ArrayList<>();

     for (Transaction transaction : transactions) {

         TransactionResponse response = new TransactionResponse();

         response.setId(transaction.getId());
         response.setAmount(transaction.getAmount());
         response.setDescription(transaction.getDescription());
         response.setTransactionDate(transaction.getTransactionDate());
         response.setType(transaction.getType());
         response.setCategory(transaction.getCategory());

         responses.add(response);
     }

     return responses;
 }

    public List<TransactionResponse> getTransactionByMonth(Year year, Month month) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No user Found"));

        int id = user.getId();

        YearMonth yearMonth = YearMonth.of(year.getValue(), month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Transaction> transactions =
                transactionRepository.findByUserIdAndTransactionDateBetween(id, startDate, endDate);

        List<TransactionResponse> res = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResponse response = new TransactionResponse();
            response.setId(transaction.getId());
            response.setAmount(transaction.getAmount());
            response.setDescription(transaction.getDescription());
            response.setTransactionDate(transaction.getTransactionDate());
            response.setType(transaction.getType());
            response.setCategory(transaction.getCategory());

            res.add(response);
        }

        return res;
    }


 public TransactionResponse updateTransaction(int transactionId , TransactionRequest request){
     String username = SecurityContextHolder.getContext().getAuthentication().getName();

     User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new NoSuchElementException("No user Found with this Transaction"));

     int userId = user.getId();

     Optional<Transaction> transactions = transactionRepository.findByIdAndUserId(transactionId, userId);

     if (transactions.isEmpty()) {
         throw new NoSuchElementException("Transaction not found or you are not authorized to update it.");
     }
     Transaction n = transactions.get();
     n.setAmount(request.getAmount());
     n.setDescription(request.getDescription());
     n.setTransactionDate(request.getTransactionDate());
     n.setType(request.getType());
     n.setCategory(request.getCategory());

     Transaction x = transactionRepository.save(n);

     TransactionResponse res = new TransactionResponse();
     res.setId(x.getId());
     res.setAmount(x.getAmount());
     res.setDescription(x.getDescription());
     res.setTransactionDate(x.getTransactionDate());
     res.setType(x.getType());
     res.setCategory(x.getCategory());

     return res;

 }


 public void deleteTransaction(int transactionId){
     String username = SecurityContextHolder.getContext().getAuthentication().getName();
     User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new NoSuchElementException("No user Found with username: " + username));

     int userId = user.getId();
     Optional<Transaction> transactions = transactionRepository.findByIdAndUserId(transactionId, userId);

     if (transactions.isEmpty()) {
         throw new NoSuchElementException("Transaction not found or you are not authorized to delete it.");
     }

     Transaction t = transactions.get();

     transactionRepository.delete(t);
 }

public TotalExpenseResponse getTotalMonthlyExpense(Month month,Year year){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No user Found with username: " + username));

        int userId = user.getId();
        LocalDate startDate =
                LocalDate.of(year.getValue(), month,1);

        LocalDate endDate =
                LocalDate.of(year.getValue(), month, month.length(year.isLeap()));

        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBetween(
                userId,
                startDate,
                endDate
        );
        double totalExpense = 0;
        for(Transaction transaction : transactions){
            totalExpense =+ transaction.getAmount();
        }


        TotalExpenseResponse response = new TotalExpenseResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotalExpense(totalExpense);
        return response;
}


}
