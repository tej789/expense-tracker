package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.model.BudgetAlert;
import com.expensetracker.api.model.BudgetAlertType;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetAlertRepository;
import com.expensetracker.api.service.BudgetAlertService;
import com.expensetracker.api.service.CurrentUserService;
import com.expensetracker.api.service.EmailService;
import com.expensetracker.api.service.MonthlySummaryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

@Service
public class BudgetAlertServiceImpl implements BudgetAlertService {

    private final MonthlySummaryService monthlySummaryService;
    private final CurrentUserService currentUserService;
    private final EmailService emailService;
    private final BudgetAlertRepository budgetAlertRepository;

    public BudgetAlertServiceImpl(
            MonthlySummaryService monthlySummaryService,
            CurrentUserService currentUserService,
            EmailService emailService,
            BudgetAlertRepository budgetAlertRepository
    ) {
        this.monthlySummaryService = monthlySummaryService;
        this.currentUserService = currentUserService;
        this.emailService = emailService;
        this.budgetAlertRepository = budgetAlertRepository;
    }

    @Override
    public void checkBudgetAlert(Month month, Year year, CategoryType category) {
        User user = currentUserService.getCurrentUser();

        MonthlySummaryResponse summary =
                monthlySummaryService.getSummaryOfCategory(
                        month, year, category
                );

        BigDecimal budget = summary.getBudget();
        BigDecimal spent = summary.getSpent();

        if (budget == null || budget.signum() <= 0 || spent == null) {
            return;
        }

        BigDecimal warning = budget.multiply(new BigDecimal("0.80"));

        BudgetAlertType alertType;
        String subject;
        String message;

        if (spent.compareTo(budget) >= 0) {
            alertType = BudgetAlertType.EXCEEDED_100_PERCENT;
            subject = "Budget limit reached: " + category;

            message = "Your spending for " + category
                    + " has reached or exceeded your budget.\n"
                    + "Month: " + month + " " + year + "\n"
                    + "Budget: " + budget + "\n"
                    + "Spent: " + spent + "\n"
                    + "Remaining: " + summary.getRemaining();
        }
        else if (spent.compareTo(warning) >= 0) {
            alertType = BudgetAlertType.WARNING_80_PERCENT;
            subject = "Budget warning: " + category;

            message = "You have used at least 80% of your "
                    + category + " budget.\n"
                    + "Month: " + month + " " + year + "\n"
                    + "Budget: " + budget + "\n"
                    + "Spent: " + spent + "\n"
                    + "Remaining: " + summary.getRemaining();
        }
        else {
            return;
        }

        boolean alreadySent = budgetAlertRepository
                        .existsByUserAndCategoryAndMonthAndYearAndAlertType(
                                user, category, month,
                                year, alertType
                        );

        if (alreadySent) {
            return;
        }

        emailService.sendEmail(user.getMail(), subject, message);

        BudgetAlert alert = new BudgetAlert();
        alert.setUser(user);
        alert.setCategory(category);
        alert.setMonth(month);
        alert.setYear(year);
        alert.setAlertType(alertType);


        budgetAlertRepository.save(alert);
    }
}
