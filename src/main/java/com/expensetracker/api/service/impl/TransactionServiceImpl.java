package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.TransactionSpecifications;
import com.expensetracker.api.service.BudgetAlertService;
import com.expensetracker.api.service.CurrentUserService;
import com.expensetracker.api.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CurrentUserService currentUserService;
    private final AmountValidationHandler amountValidationHandler;
    private final BudgetAlertService budgetAlertService;



    public TransactionServiceImpl(BudgetAlertService budgetAlertService,TransactionRepository transactionRepository,CurrentUserService currentUserService,DateValidationHandler dateValidationHandler,AmountValidationHandler amountValidationHandler){
        this.transactionRepository = transactionRepository;
        this.currentUserService = currentUserService;
        this.amountValidationHandler = amountValidationHandler;
        this.budgetAlertService = budgetAlertService;


        amountValidationHandler.setNext(dateValidationHandler);
    }
    private void checkBudgetAlert(Transaction transaction) {
        if (transaction.getType() == TransactionType.EXPENSE) {
            LocalDate date = transaction.getTransactionDate();

            budgetAlertService.checkBudgetAlert(
                    date.getMonth(),
                    Year.of(date.getYear()),
                    transaction.getCategory()
            );
        }
    }

    @Override
    public TransactionResponse addTransaction(TransactionRequest request) {

        amountValidationHandler.validate(request);

        User user = currentUserService.getCurrentUser();

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setUser(user);

        transactionRepository.save(transaction);

        checkBudgetAlert(transaction);

        TransactionResponse res = new TransactionResponse();

        res.setId(transaction.getId());
        res.setAmount(transaction.getAmount());
        res.setDescription(transaction.getDescription());
        res.setTransactionDate(transaction.getTransactionDate());
        res.setType(transaction.getType());
        res.setCategory(transaction.getCategory());
        res.setPaymentMethod(transaction.getPaymentMethod());
        return res;

    }



    @Override
    public Page<TransactionResponse> getTransactions(int page, int size){
            User user =   currentUserService.getCurrentUser();
            int id = user.getId();

            Pageable pageable = PageRequest.of(page,size);
            Page<Transaction> transactionPage = transactionRepository.findByUserIdAndActiveTrue(id,pageable);

            return transactionPage.map(transaction -> {
                TransactionResponse response = new TransactionResponse();

                response.setId(transaction.getId());
                response.setAmount(transaction.getAmount());
                response.setDescription(transaction.getDescription());
                response.setTransactionDate(transaction.getTransactionDate());
                response.setCategory(transaction.getCategory());
                response.setPaymentMethod(transaction.getPaymentMethod());
                response.setType(transaction.getType());

return response;
            });
        }



    @Override
    public List<TransactionResponse> getTransactionByMonth(Year year, Month month) {
        User user = currentUserService.getCurrentUser();

        int id = user.getId();

        LocalDate startDate = LocalDate.of(year.getValue(), month, 1);

        LocalDate endDate = LocalDate.of(
                year.getValue(),
                month,
                month.length(year.isLeap())
        );

        List<Transaction> transactions =
                transactionRepository.findByUserIdAndTransactionDateBetweenAndActiveTrue(id, startDate, endDate);

        List<TransactionResponse> res = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResponse response = new TransactionResponse();
            response.setId(transaction.getId());
            response.setAmount(transaction.getAmount());
            response.setDescription(transaction.getDescription());
            response.setTransactionDate(transaction.getTransactionDate());
            response.setType(transaction.getType());
            response.setCategory(transaction.getCategory());
            response.setPaymentMethod(transaction.getPaymentMethod());

            res.add(response);
        }

        return res;
    }

    @Override
    public TransactionResponse updateTransaction(int transactionId , TransactionRequest request){
        User user = currentUserService.getCurrentUser();
        int userId = user.getId();

        Optional<Transaction> transactions = transactionRepository.findByIdAndUserIdAndActiveTrue(transactionId, userId);

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("Transaction not found or you are not authorized to update it.");
        }

        amountValidationHandler.validate(request);

        Transaction n = transactions.get();
        n.setAmount(request.getAmount());
        n.setDescription(request.getDescription());
        n.setTransactionDate(request.getTransactionDate());
        n.setType(request.getType());
        n.setCategory(request.getCategory());
        n.setPaymentMethod(request.getPaymentMethod());

        Transaction x = transactionRepository.save(n);

        checkBudgetAlert(x);

        TransactionResponse res = new TransactionResponse();
        res.setId(x.getId());
        res.setAmount(x.getAmount());
        res.setDescription(x.getDescription());
        res.setTransactionDate(x.getTransactionDate());
        res.setType(x.getType());
        res.setCategory(x.getCategory());
        res.setPaymentMethod(x.getPaymentMethod());

        return res;

    }

    @Override
    public void deleteTransaction(int transactionId){
        User user = currentUserService.getCurrentUser();

        int userId = user.getId();
        Optional<Transaction> transactions = transactionRepository.findByIdAndUserIdAndActiveTrue(transactionId, userId);

        if (transactions.isEmpty()) {
            throw new NoSuchElementException("Transaction not found or you are not authorized to delete it.");
        }

        Transaction t = transactions.get();

        t.setActive(false);
        transactionRepository.save(t);
    }

    @Override
    public TotalExpenseResponse getTotalMonthlyExpense(Month month, Year year){
        User user = currentUserService.getCurrentUser();

        int userId = user.getId();
        LocalDate startDate =
                LocalDate.of(year.getValue(), month,1);

        LocalDate endDate =
                LocalDate.of(year.getValue(), month, month.length(year.isLeap()));

        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBetweenAndActiveTrue(
                userId,
                startDate,
                endDate
        );
        BigDecimal totalExpense = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            totalExpense = totalExpense.add(transaction.getAmount());
        }


        TotalExpenseResponse response = new TotalExpenseResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotalExpense(totalExpense);
        return response;
    }


    @Override
    public Page<TransactionResponse> filterTransactions(
            LocalDate startDate,
            LocalDate endDate,
            CategoryType category,
            TransactionType type,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            int page,
            int size
    ){

        if(page<0 || size <1||size>100){
            throw new IllegalArgumentException("Invalid pagination values");
        }

        if(startDate != null && endDate !=null
        && startDate.isAfter(endDate)){
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }

        if (minAmount != null && minAmount.signum() < 0
                || maxAmount != null && maxAmount.signum() < 0) {
            throw new IllegalArgumentException(
                    "Amounts cannot be negative"
            );
        }

        if (minAmount != null && maxAmount != null
                && minAmount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException(
                    "Minimum amount cannot exceed maximum amount"
            );
        }

        User user = currentUserService.getCurrentUser();

        var specification = TransactionSpecifications.filters(
                user.getId(),
                startDate,
                endDate,
                category,
                type,
                minAmount,
                maxAmount
        );

        Pageable pageable = PageRequest.of(page,size);

        return transactionRepository.findAll(specification,pageable)
                .map(transaction ->{
                    TransactionResponse response = new TransactionResponse();
                    response.setId(transaction.getId());
                    response.setAmount(transaction.getAmount());
                    response.setDescription(transaction.getDescription());
                    response.setTransactionDate(
                            transaction.getTransactionDate()
                    );
                    response.setCategory(transaction.getCategory());
                    response.setPaymentMethod(transaction.getPaymentMethod());
                    response.setType(transaction.getType());
                    return response;
                });


    }

    @Override
    public byte[] exportMonthlyTransactions(Month month,Year year){
        User user = currentUserService.getCurrentUser();

        LocalDate startDate =LocalDate.of(year.getValue(),month,1);

        LocalDate endDate = LocalDate.of(
                year.getValue(),
                month,
                month.length(year.isLeap())
        );

        List<Transaction> transactions = transactionRepository
                        .findByUserIdAndTransactionDateBetweenAndActiveTrue(
                                user.getId(),
                                startDate,
                                endDate
                        );

        StringBuilder csv = new StringBuilder();

        csv.append("Date,Description,Category,Type,Amount\r\n");

        for (Transaction transaction : transactions) {
            csv.append(transaction.getTransactionDate()).append(",");
            csv.append(escapeCsv(transaction.getDescription())).append(",");
            csv.append(escapeCsv(
                    transaction.getCategory() == null
                            ? ""
                            : transaction.getCategory().toString()
            )).append(",");
            csv.append(escapeCsv(
                    transaction.getType() == null
                            ? ""
                            : transaction.getType().toString()
            )).append(",");
            csv.append(transaction.getAmount()).append("\r\n");
        }

        return csv.toString().getBytes(
                java.nio.charset.StandardCharsets.UTF_8
        );

    }

    @Override
    public String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        // Reduce spreadsheet formula-injection risk in text fields.
        if (!value.isEmpty()
                && "=+-@".indexOf(value.charAt(0)) >= 0) {
            value = "'" + value;
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

}
