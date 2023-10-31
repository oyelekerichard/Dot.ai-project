package com.dot.ai.service;


import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.ApiResponse;

public interface UserService {
    ApiResponse createAccount(UserRequest userRequest);
}
