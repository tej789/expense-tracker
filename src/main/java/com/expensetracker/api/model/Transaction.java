package com.expensetracker.api.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.SplittableRandom;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private String description;
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;



}
