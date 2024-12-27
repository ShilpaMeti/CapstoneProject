package com.example.rest.expense.service;

import com.example.rest.expense.entity.Expense;
import com.example.rest.expense.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        expenseService = new ExpenseService(expenseRepository);
    }

    @Test
    void testAddExpense() {
        Expense expense = new Expense(1L, 100.50, "Groceries", new java.util.Date());
        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense createdExpense = expenseService.addExpense(expense);

        assertNotNull(createdExpense);
        assertEquals(100.50, createdExpense.getAmount());
        assertEquals("Groceries", createdExpense.getDescription());
    }

    @Test
    void testGetAllExpenses() {
        Expense expense1 = new Expense(1L, 100.50, "Groceries", new java.util.Date());
        Expense expense2 = new Expense(2L, 50.75, "Transport", new java.util.Date());
        List<Expense> expenses = Arrays.asList(expense1, expense2);

        when(expenseRepository.findAll()).thenReturn(expenses);

        List<Expense> result = expenseService.getAllExpenses();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteExpenseSuccess() {
        when(expenseRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = expenseService.deleteExpense(1L);

        // Assert
        assertTrue(result);
        verify(expenseRepository, times(1)).deleteById(1L);
        verify(expenseRepository, times(1)).existsById(1L);
    }

    @Test
    void testDeleteExpenseNotFound() {
        when(expenseRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = expenseService.deleteExpense(1L);

        // Assert
        assertFalse(result);
        verify(expenseRepository, times(0)).deleteById(1L);
        verify(expenseRepository, times(1)).existsById(1L);
    }
}