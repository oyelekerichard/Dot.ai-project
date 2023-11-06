package com.dot.ai.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_transactions_summary")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String transactionStatus;

    private Double total;

    private Integer transactionCount;

    @CreationTimestamp
    private LocalDateTime dateCreated;
}
