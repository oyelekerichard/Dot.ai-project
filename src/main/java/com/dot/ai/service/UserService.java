package com.dot.ai.service;

import com.dot.ai.dtos.request.AccountDetailsRequest;
import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.ApiResponse;

public interface UserService {
    ApiResponse createAccount(UserRequest userRequest);

    ApiResponse getAccountBalanceEnquiry(AccountDetailsRequest accountDetailsRequest);

    ApiResponse nameEnquiry(AccountDetailsRequest accountDetailsRequest);

}
