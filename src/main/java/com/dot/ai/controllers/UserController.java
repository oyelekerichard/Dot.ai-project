package com.dot.ai.controllers;

import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ApiResponse createUserAccount(@RequestBody UserRequest userRequest) {

        log.info("HERE AT THE START");
        return userService.createAccount(userRequest);
    }
}
