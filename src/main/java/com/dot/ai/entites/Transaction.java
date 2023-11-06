package com.dot.ai.entites;

import com.dot.ai.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionReference;
    private BigDecimal amount;
    private Double transactionFee;
    private String billedAmount;
    private String description;
    private String transactionStatus;
    private Boolean commissionWorthy;
    private Double commission;
    private String userId;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Transaction(String transactionReference, BigDecimal amount, Double transactionFee, String billedAmount, String description, String transactionStatus, Boolean commissionWorthy, Double commission, String userId, LocalDateTime dateCreated) {
        this.transactionReference = transactionReference;
        this.amount = amount;
        this.transactionFee = transactionFee;
        this.billedAmount = billedAmount;
        this.description = description;
        this.transactionStatus = transactionStatus;
        this.commissionWorthy = commissionWorthy;
        this.commission = commission;
        this.userId = userId;
        this.dateCreated = dateCreated;
    }
}
