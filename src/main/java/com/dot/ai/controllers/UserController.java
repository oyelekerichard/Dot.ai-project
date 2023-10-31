package com.dot.ai.controllers;

import com.dot.ai.dtos.request.AccountDetailsRequest;
import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.TransferService;
import com.dot.ai.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransferService transferService;

    @PostMapping("/createUser")
    public ApiResponse createUserAccount(@RequestBody UserRequest userRequest) {

        log.info("HERE AT THE createUserAccount");
        return userService.createAccount(userRequest);
    }

    @GetMapping("/getUserBalance")
    public ApiResponse getBalanceEnquiry(@RequestBody AccountDetailsRequest request) {

        log.info("HERE AT THE getBalanceEnquiry");
        return userService.getAccountBalanceEnquiry(request);
    }

    @GetMapping("/getNameEnquiry")
    public ApiResponse getUserFullname(@RequestBody AccountDetailsRequest request) {

        log.info("HERE AT THE getUserFullname");
        return userService.nameEnquiry(request);
    }
}
