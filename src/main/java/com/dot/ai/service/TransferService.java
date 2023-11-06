package com.dot.ai.service;

import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.DotApiResponse;
import com.dot.ai.dtos.response.TransactionSummary;

import java.util.List;

public interface TransferService {

    DotApiResponse transfer(TransferRequest transferRequest);

    DotApiResponse getAllTransactions();

    DotApiResponse getAllTransactions(String status, String userId, String startDate, String endDate);

    DotApiResponse getTransactionSummary(String startDate, String endDate);


}
