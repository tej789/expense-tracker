package com.expensetracker.api.repository;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    List<Transaction> findByUserId(int userId);

    Optional<Transaction> findByIdAndUserId(int id, int userId);

//
//    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t " +
//            "WHERE t.user.id = :userId " +
//            "AND t.category = :category " +
//            "AND t.type = :type " +
//            "AND FUNCTION('MONTH', t.transactionDate) = :month " +
//            "AND FUNCTION('YEAR', t.transactionDate) = :year")
//    double getTotalAmountByUserCategoryAndMonth(
//            @Param("userId") int userId,
//            @Param("category") CategoryType category,
//            @Param("type") TransactionType type,
//            @Param("month") int month,
//            @Param("year") int year
//    );

}
