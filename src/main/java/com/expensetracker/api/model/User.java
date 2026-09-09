package com.expensetracker.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    private String firstname;
    private String lastname;


    @Email
    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @Column(nullable = false, length = 60)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean active = true;




    // for email verification
    @Column(nullable = false)
    private boolean emailVerified = false;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiry;
}

