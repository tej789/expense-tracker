package com.expensetracker.api.repository;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    List<Transaction> findByUserId(int userId);

    Optional<Transaction> findByIdAndUserId(int id, int userId);



    List<Transaction> findByUserIdAndTransactionDateBetween(
            int userId,
            LocalDate startDate,
            LocalDate endDate
    );
    void deleteByUserId(int userId);
}


