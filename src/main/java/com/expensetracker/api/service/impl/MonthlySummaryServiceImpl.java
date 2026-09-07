package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.service.CurrentUserService;
import com.expensetracker.api.service.MonthlySummaryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlySummaryServiceImpl implements MonthlySummaryService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final CurrentUserService currentUserService;

    public MonthlySummaryServiceImpl(
            BudgetRepository budgetRepository,
            TransactionRepository transactionRepository,
            CurrentUserService currentUserService) {

        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.currentUserService =currentUserService;
    }

    @Override
    public MonthlySummaryResponse getSummaryOfCategory(
            Month month,
            Year year,
            CategoryType category
    ){

        User user = currentUserService.getCurrentUser();

        int userId = user.getId();

        Budget budget = budgetRepository.findByUserIdAndMonthAndYearAndCategory(
                userId,
                month,
                year,
                category
        );

        double budgetAmount =0;

        if(budget != null){
            budgetAmount =budget.getAmount();
        }
        LocalDate startDate =
                LocalDate.of(year.getValue(), month, 1);

        LocalDate endDate =
                LocalDate.of(
                        year.getValue(),
                        month,
                        month.length(year.isLeap())
                );


        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBetween(
                userId,
                startDate,
                endDate
        );

        double spent= 0;

        for(Transaction transaction : transactions)
        {
            if(transaction.getCategory() == category ){
                spent = spent + transaction.getAmount();
            }
        }

        MonthlySummaryResponse res =  new MonthlySummaryResponse(
                category,
                budgetAmount,
                spent
        );

        return res;
    }

    @Override
    public List<MonthlySummaryResponse> getMonthlySummary(
            Month month,
            Year year) {

        User user = currentUserService.getCurrentUser();

        int userId = user.getId();

        List<Budget> budgets =
                budgetRepository.findByUserIdAndMonthAndYear(
                        userId,
                        month,
                        year
                );

        LocalDate startDate =
                LocalDate.of(year.getValue(), month, 1);

        LocalDate endDate =
                LocalDate.of(
                        year.getValue(),
                        month,
                        month.length(year.isLeap())
                );

        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBetween(
                userId,
                startDate,
                endDate
        );

        List<MonthlySummaryResponse> summary = new ArrayList<>();

        for (CategoryType category : CategoryType.values()) {

            double budgetAmount = 0;
            double spent = 0;

            for (Budget budget : budgets) {

                if (budget.getCategory() == category) {
                    budgetAmount = budget.getAmount();
                    break;
                }
            }

            for (Transaction transaction : transactions) {

                if (transaction.getCategory() == category) {
                    spent = spent + transaction.getAmount();
                }
            }

            if (budgetAmount > 0 || spent > 0) {

                MonthlySummaryResponse response =
                        new MonthlySummaryResponse(
                                category,
                                budgetAmount,
                                spent
                        );

                summary.add(response);
            }
        }

        return summary;
    }


}
