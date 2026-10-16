package com.expensetracker.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "login_attempts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_login_attempt_username",
                        columnNames = "username"
                )
        }
)
@Getter
@Setter
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true,length =100)
    private String username;

    @Column(nullable = false)
    private int failedAttempts = 0;

    private LocalDateTime blockedUntill;

    private LocalDateTime lastFailedAt;
}
