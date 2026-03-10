package com.example.expensetracker.config;

import com.example.expensetracker.entity.Transaction;
import com.example.expensetracker.entity.TransactionType;
import com.example.expensetracker.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(TransactionRepository transactionRepository) {
        return args -> {
            // Check if database is empty before seeding
            if (transactionRepository.count() == 0) {
                System.out.println("No transaction data found. Seeding initial test data...");
                
                List<Transaction> seedData = List.of(
                    new Transaction(null, new BigDecimal("15000000"), LocalDate.now().minusDays(5), "Lương tháng", TransactionType.INCOME),
                    new Transaction(null, new BigDecimal("50000"), LocalDate.now().minusDays(2), "Ăn trưa", TransactionType.EXPENSE),
                    new Transaction(null, new BigDecimal("200000"), LocalDate.now(), "Đi siêu thị", TransactionType.EXPENSE)
                );
                
                transactionRepository.saveAll(seedData);
            }
        };
    }
}
