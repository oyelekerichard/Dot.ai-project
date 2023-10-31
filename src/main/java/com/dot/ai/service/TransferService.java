package com.dot.ai.service;

import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.DotApiResponse;

public interface TransferService {

    DotApiResponse creditAccount(CreditDebitRequest request);

    DotApiResponse debitAccount(CreditDebitRequest request);

    DotApiResponse transfer(TransferRequest transferRequest);

    DotApiResponse getAllTransactions();

}
