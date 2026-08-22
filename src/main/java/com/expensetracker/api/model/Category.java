package com.expensetracker.api.model;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String category;

    @ManyToOne
    private User user;

}
