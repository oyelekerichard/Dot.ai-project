package com.dot.ai.serviceimpl;

import com.dot.ai.configs.Utility;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.AccountDetails;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.entites.User;
import com.dot.ai.enums.Status;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse createAccount(UserRequest userRequest) {
        /**
         * CREATE A NEW USER AND SAVE NEW USER TO DB
         * Check if new user created already exists by checking in thee db for the email provided
         */

        if ((userRepository.existsByEmail(userRequest.getEmail()))) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(Status.USER_EXISTS.getStatusCode())
                    .message(Status.USER_EXISTS.getStatusMessage())
                    .result(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOrigin())
                .accountNumber(Utility.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternatePhonenumber(userRequest.getAlternatePhonenumber())
                .status("ACTIVE")
                .build();

//        Save the new user into the userRepository

        User saveNewUser = userRepository.save(newUser);
        return ApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(AccountDetails.builder()
                        .accountNumber(saveNewUser.getAccountNumber())
                        .accountBalance(saveNewUser.getAccountBalance())
                        .accountName(saveNewUser.getFirstName()+" "+saveNewUser.getLastName()+" "+saveNewUser.getOtherName())
                        .build())
                .build();
    }
}
