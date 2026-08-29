package com.expensetracker.api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.time.Year;

@Entity
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;

    @Enumerated(EnumType.STRING)
    private Month month;
    private Year year;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}



