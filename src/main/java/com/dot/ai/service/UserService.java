package com.dot.ai.service;

import com.dot.ai.dtos.request.AccountDetailsRequest;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.DotApiResponse;

public interface UserService {
    DotApiResponse createAccount(UserRequest userRequest);

    DotApiResponse getAccountBalanceEnquiry(AccountDetailsRequest accountDetailsRequest);
}
