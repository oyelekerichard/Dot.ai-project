package com.dot.ai.repositories;

import com.dot.ai.entites.TransactionSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummaryEntity, Long> {
}
