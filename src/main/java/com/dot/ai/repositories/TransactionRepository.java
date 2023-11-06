package com.dot.ai.repositories;

import com.dot.ai.dtos.response.TransactionSummary;
import com.dot.ai.entites.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionByTransactionStatus(String transactionStatus);

    @Query(value = "SELECT * FROM transactions WHERE date_created BETWEEN CAST(?1 AS DATE)" +
            " AND DATE_ADD(CAST(?1 AS DATE), INTERVAL 1 day) and user_id = ?2", nativeQuery = true)
    List<Transaction> getAllTransactionsByDateAndUserId(String dateOfTransaction, String userId);

    @Query(value = "SELECT * FROM transactions WHERE transaction_status = ?1", nativeQuery = true)
    List<Transaction> getDailyTransactionsByStatus(String status);


    @Query(value = "SELECT * FROM transactions WHERE date_created BETWEEN CAST(?1 AS DATE)" +
            " AND DATE_ADD(CAST(?1 AS DATE), INTERVAL 1 day)", nativeQuery = true)
    List<Transaction> getDailyTransactions(String startDate);

    @Query(value = "SELECT transaction_status AS TransactionStatus, sum(amount) " +
            " AS Total, count(transaction_status) AS TransactionCount " +
            "FROM transactions WHERE date_created BETWEEN CAST(?1 AS DATE) AND " +
            "  DATE_ADD(CAST(?2 AS DATE), INTERVAL 1 day) GROUP BY transactionStatus", nativeQuery = true)
    List<TransactionSummary> getTransactionSummary(String startDate, String endDate);

}
