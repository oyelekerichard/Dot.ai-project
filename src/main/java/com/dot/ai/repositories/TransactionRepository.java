package com.dot.ai.repositories;

import com.dot.ai.entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getTransactionByTransactionStatus(String transactionStatus);
}
