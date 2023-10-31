package com.dot.ai.controllers;

import com.dot.ai.dtos.request.AccountDetailsRequest;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.DotApiResponse;
import com.dot.ai.service.TransferService;
import com.dot.ai.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Account Management APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransferService transferService;

    @Operation(summary = "Create New User Account",
            description = "Creating a new user and generating a new account number")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 201 CREATED")
    @PostMapping("/createUser")
    public DotApiResponse createUserAccount(@RequestBody UserRequest userRequest) {

        log.info("HERE AT THE createUserAccount");
        return userService.createAccount(userRequest);
    }

    @Operation(summary = "Get User Account Balance",
            description = "Get User Account Balance")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 201 CREATED")
    @GetMapping("/getUserBalance")
    public DotApiResponse getBalanceEnquiry(@RequestBody AccountDetailsRequest request) {

        log.info("HERE AT THE getBalanceEnquiry");
        return userService.getAccountBalanceEnquiry(request);
    }

    @Operation(summary = "Get Existing User Account Full name",
            description = "Get the full name of the user")
    @ApiResponse(responseCode = "201",
            description = "HTTP Status 201 CREATED")
    @GetMapping("/getNameEnquiry")
    public DotApiResponse getUserFullname(@RequestBody AccountDetailsRequest request) {

        log.info("HERE AT THE getUserFullname");
        return userService.nameEnquiry(request);
    }
}
