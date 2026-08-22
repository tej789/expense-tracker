package com.expensetracker.api.model;


import jakarta.persistence.*;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private int month;
    private int year;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
}
