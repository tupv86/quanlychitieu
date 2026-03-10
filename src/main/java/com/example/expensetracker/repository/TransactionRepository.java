package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year ORDER BY t.date DESC")
    List<Transaction> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
