package com.dot.ai.schedulers;

import com.dot.ai.configs.Utility;
import com.dot.ai.entites.Transaction;
import com.dot.ai.enums.TransactionStatus;
import com.dot.ai.repositories.TransactionRepository;
import com.dot.ai.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransferService transferService;

    @Value("${dot.ai.commission.account}")
    private String dotCommissionAccount;

    @Scheduled(cron = "@daily")
    @Async
    public void updateTransactionCommissionFee() {
        log.info("Update transactions with commissions on the account");

        Double txnFee = 0.00;
        Double commisionFee = 0.00;

        List<Transaction> transactions = transactionRepository.getTransactionByTransactionStatus("Successful");

        log.info("successful transactions :: " + transactions);

        for (Transaction tx : transactions) {

            txnFee = tx.getTransactionFee();
            commisionFee = Utility.commissionFee(txnFee);

            Transaction.builder()
                    .transactionStatus(TransactionStatus.COMPLETED.toString())
                    .commissionWorthy(true)
                    .commission(commisionFee)
                    .build();

//            transferService.transfer(tx.)
        }

    }

    @Scheduled(cron = "@daily")
    @Async
    public void getDailyTransactionSummary() {

    }
}