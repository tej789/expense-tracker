package com.expensetracker.api.model;

import jakarta.persistence.*;
import java.time.Month;
import java.time.Year;

@Entity
@Table(
        name = "budget_alerts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_budget_alert_period",
                        columnNames = {
                                "user_id",
                                "category",
                                "month",
                                "year",
                                "alert_type"
                        }
                )
        }
)
public class BudgetAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Month month;

    @Column(nullable = false)
    private Year year;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    private BudgetAlertType alertType;

    public BudgetAlert() {
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public BudgetAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(BudgetAlertType alertType) {
        this.alertType = alertType;
    }
}
