package com.dot.ai.service;

import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.ApiResponse;

public interface TransferService {

    ApiResponse creditAccount(CreditDebitRequest request);

    ApiResponse debitAccount(CreditDebitRequest request);

    ApiResponse transfer(TransferRequest transferRequest);

    ApiResponse getAllTransactions();

}
