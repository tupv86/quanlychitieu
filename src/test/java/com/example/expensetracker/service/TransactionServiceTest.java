package com.example.expensetracker.service;

import com.example.expensetracker.entity.Transaction;
import com.example.expensetracker.entity.TransactionType;
import com.example.expensetracker.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateTotalIncome() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, new BigDecimal("100"), LocalDate.now(), "Inc 1", TransactionType.INCOME),
                new Transaction(2L, new BigDecimal("50"), LocalDate.now(), "Exp 1", TransactionType.EXPENSE),
                new Transaction(3L, new BigDecimal("200"), LocalDate.now(), "Inc 2", TransactionType.INCOME)
        );

        BigDecimal totalIncome = transactionService.calculateTotalIncome(transactions);
        assertEquals(new BigDecimal("300"), totalIncome);
    }

    @Test
    void testCalculateTotalExpense() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, new BigDecimal("100"), LocalDate.now(), "Inc 1", TransactionType.INCOME),
                new Transaction(2L, new BigDecimal("50"), LocalDate.now(), "Exp 1", TransactionType.EXPENSE),
                new Transaction(3L, new BigDecimal("200"), LocalDate.now(), "Inc 2", TransactionType.INCOME)
        );

        BigDecimal totalExpense = transactionService.calculateTotalExpense(transactions);
        assertEquals(new BigDecimal("50"), totalExpense);
    }

    @Test
    void testCalculateBalance() {
        BigDecimal balance = transactionService.calculateBalance(new BigDecimal("300"), new BigDecimal("50"));
        assertEquals(new BigDecimal("250"), balance);
    }

    @Test
    void testSaveAndDelete() {
        Transaction t = new Transaction(1L, new BigDecimal("100"), LocalDate.now(), "Inc", TransactionType.INCOME);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(t);

        Transaction saved = transactionService.saveTransaction(t);
        assertEquals(t.getId(), saved.getId());
        verify(transactionRepository, times(1)).save(t);

        transactionService.deleteTransaction(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }
}
