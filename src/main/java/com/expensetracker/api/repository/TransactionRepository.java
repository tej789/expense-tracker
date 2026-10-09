package com.expensetracker.api.repository;

import com.expensetracker.api.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    List<Transaction> findByUserIdAndActiveTrue(int userId);

    Page<Transaction> findByUserIdAndActiveTrue(int userId , Pageable pageable);

    Optional<Transaction> findByIdAndUserIdAndActiveTrue(int id, int userId);

    List<Transaction> findByUserIdAndTransactionDateBetweenAndActiveTrue(
            int userId,
            LocalDate startDate,
            LocalDate endDate
    );

}


