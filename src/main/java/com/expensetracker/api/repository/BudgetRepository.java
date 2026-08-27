package com.expensetracker.api.repository;

import com.expensetracker.api.model.Budget;
import com.expensetracker.api.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Integer> {

    Optional<Budget> findByUserIdAndCategoryAndMonthAndYear(int userId, CategoryType category, int month, int year);


    List<Budget> findByUserIdAndMonthAndYear(int userId, int month, int year);


    Optional<Object> findByUserIdAndCategoryAndMonthAndYear(int id, CategoryType category, Month month, Year year);
}
