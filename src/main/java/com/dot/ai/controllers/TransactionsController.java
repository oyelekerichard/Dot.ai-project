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

    @PostMapping("/processTransfer")
    public DotApiResponse processTransfer(@RequestBody TransferRequest transferRequest) {

        log.info("HERE AT THE processTransfer");
        return transferService.transfer(transferRequest);
    }


    @GetMapping("/getAllTransactions")
    public DotApiResponse getAllTransactions(@RequestParam(required = false) String status,
                                             @RequestParam(required = false) String userId,
                                             @RequestParam(required = false) String startDate,
                                             @RequestParam(required = false) String endDate) {

        log.info("HERE AT THE getAllTransactions");
        return transferService.getAllTransactions(status, userId, startDate, endDate);
    }

    @GetMapping("/getTransactionSummary")
    public DotApiResponse getTransactionSummary(@RequestParam String startDate,
                                                @RequestParam String endDate) {

        log.info("HERE AT THE getAllTransactions summary");
        return transferService.getTransactionSummary(startDate, endDate);
    }
}


