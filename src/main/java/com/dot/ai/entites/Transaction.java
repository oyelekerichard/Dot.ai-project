package com.dot.ai.entites;

import com.dot.ai.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "transaction_reference")
    private String transactionReference;


    @Column(name = "amount")
    private double amount;


    @Column(name = "transaction_fee")
    private String transactionFee;
    @Column(name = "billed_amount")
    private String billedAmount;
    @Column(name = "description")
    private String  description;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "transaction_status")
    private TransactionStatus status;

    @Column(name = "commission_worthy")
    private Boolean commissionWorthy;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "user_id")
    private String userId;
}
