package com.dot.ai.controllers;

import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.repositories.TransactionRepository;
import com.dot.ai.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/transactions")
public class TransactionsController {
    @Autowired
    private TransferService transferService;

    @PostMapping("/creditAccount")
    public ApiResponse creditAccount(@RequestBody CreditDebitRequest request) {

        log.info("HERE AT THE creditAccount");
        return transferService.creditAccount(request);
    }

    @PostMapping("/debitAccount")
    public ApiResponse debitAccount(@RequestBody CreditDebitRequest request) {

        log.info("HERE AT THE debitAccount");
        return transferService.debitAccount(request);
    }

    @PostMapping("/processTransfer")
    public ApiResponse processTransfer(@RequestBody TransferRequest transferRequest) {

        log.info("HERE AT THE processTransfer");
        return transferService.transfer(transferRequest);
    }

    @GetMapping("/getAllTransactions")
    public ApiResponse getAllTransactions() {

        log.info("HERE AT THE processTransfer");
        return transferService.getAllTransactions();
    }
}
