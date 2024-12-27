package com.example.rest.expense.repository;

import com.example.rest.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    //Example for JPQL
    @Query("SELECT e FROM Expense e WHERE e.amount > :amount")
    List<Expense> findByAmountGreaterThan(@Param("amount") double amount);
}
