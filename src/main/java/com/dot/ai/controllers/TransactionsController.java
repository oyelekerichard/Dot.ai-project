package com.dot.ai.controllers;

import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.DotApiResponse;
import com.dot.ai.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/transactions")
@Tag(name = "Transactions Management APIs")
public class TransactionsController {
    @Autowired
    private TransferService transferService;

    @Operation(summary = "Credit account",
            description = "Credit an account number")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 200")
    @PostMapping("/creditAccount")
    public DotApiResponse creditAccount(@RequestBody CreditDebitRequest request) {

        log.info("HERE AT THE creditAccount");
        return transferService.creditAccount(request);
    }

    @Operation(summary = "Debit an Account",
            description = "Debit and account number")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 200")
    @PostMapping("/debitAccount")
    public DotApiResponse debitAccount(@RequestBody CreditDebitRequest request) {

        log.info("HERE AT THE debitAccount");
        return transferService.debitAccount(request);
    }

    @Operation(summary = "Process transers ",
            description = "Process tranfers transactions")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 200")
    @PostMapping("/processTransfer")
    public DotApiResponse processTransfer(@RequestBody TransferRequest transferRequest) {

        log.info("HERE AT THE processTransfer");
        return transferService.transfer(transferRequest);
    }

    @Operation(summary = "get all transactions",
            description = "Get all transactions")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 200")
    @GetMapping("/getAllTransactions")
    public DotApiResponse getAllTransactions() {

        log.info("HERE AT THE processTransfer");
        return transferService.getAllTransactions();
    }
}
