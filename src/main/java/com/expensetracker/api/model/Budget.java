package com.expensetracker.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

@Entity
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @DecimalMax(value = "9999999.99", message = "Transaction amount exceeds the maximum allowed limit of 9,999,999.99")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Month month;
    private Year year;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean active = true;

}



