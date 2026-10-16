package com.expensetracker.api.repository;

import com.expensetracker.api.model.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt,Integer> {

    Optional<LoginAttempt> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}
