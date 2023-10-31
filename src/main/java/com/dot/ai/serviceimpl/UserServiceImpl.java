package com.dot.ai.serviceimpl;

import com.dot.ai.configs.Utility;
import com.dot.ai.dtos.request.AccountDetailsRequest;
import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.EmailDetails;
import com.dot.ai.dtos.request.UserRequest;
import com.dot.ai.dtos.response.AccountDetails;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.entites.User;
import com.dot.ai.enums.Status;
import com.dot.ai.repositories.EmailRepository;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.EmailService;
import com.dot.ai.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;

    @Override
    public ApiResponse createAccount(UserRequest userRequest) {
        /**
         * CREATE A NEW USER AND SAVE NEW USER TO DB
         * Check if new user created already exists by checking in thee db for the email provided
         */
        if (Utility.isValidEmail(userRequest.getEmail())) {
            if ((userRepository.existsByEmail(userRequest.getEmail()))) {
                return ApiResponse.builder()
                        .status(Status.USER_EXISTS.getStatusCode())
                        .message(Status.USER_EXISTS.getStatusMessage())
                        .result(null)
                        .build();
            }

            User newUser = User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).otherName(userRequest.getOtherName()).gender(userRequest.getGender()).address(userRequest.getAddress()).stateOfOrigin(userRequest.getStateOrigin()).accountNumber(Utility.generateAccountNumber()).accountBalance(BigDecimal.ZERO).email(userRequest.getEmail()).phoneNumber(userRequest.getPhoneNumber()).alternatePhonenumber(userRequest.getAlternatePhonenumber()).status("ACTIVE").build();

//        Save the new user into the userRepository

            User savedUser = userRepository.save(newUser);

            EmailDetails emailDetails = EmailDetails.builder().recipient(savedUser.getEmail()).subject("DOT.AI BANK ACCOUNT CREATION").messageBody("Congratulations!!! Your Dot.ai account has been created\n" + "Your Account Details :: \n" + "Account Name :: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n " + "Account Number :: " + savedUser.getAccountNumber()).build();
//        Send notification to new user via email
            emailService.sendEmailAlerts(emailDetails);

//        save sent email details into the email repository
            emailRepository.save(emailDetails);

            return ApiResponse.builder().status(Status.SUCCESS.getStatusCode()).message(Status.SUCCESS.getStatusMessage()).result(AccountDetails.builder().accountNumber(savedUser.getAccountNumber()).accountBalance(savedUser.getAccountBalance()).accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName()).build()).build();

        } else {
            return ApiResponse.builder()
                    .status(Status.INVALID_EMAIL.getStatusCode())
                    .message(Status.INVALID_EMAIL.getStatusMessage())
                    .result(null)
                    .build();
        }
    }

    @Override
    public ApiResponse getAccountBalanceEnquiry(AccountDetailsRequest accountDetailsRequest) {
//        check if a user with account number details exsists in the db

        Boolean accountExists = userRepository.existsByAccountNumber(accountDetailsRequest.getAccountNumber());

        if (!accountExists) {
            return ApiResponse.builder()
                    .status(Status.USER_DOES_NOT_EXIST.getStatusCode())
                    .message(Status.USER_DOES_NOT_EXIST.getStatusMessage())
                    .result(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(accountDetailsRequest.getAccountNumber());
        return ApiResponse.builder().status(Status.SUCCESS.getStatusCode()).message(Status.SUCCESS.getStatusMessage()).result(AccountDetails.builder().accountBalance(foundUser.getAccountBalance()).accountNumber(foundUser.getAccountNumber()).accountName(foundUser.getFirstName() + " " + foundUser.getLastName()).build()).build();
    }

    @Override
    public ApiResponse nameEnquiry(AccountDetailsRequest accountDetailsRequest) {
//        check if a user with account number details exsists in the db

        Boolean accountExists = userRepository.existsByAccountNumber(accountDetailsRequest.getAccountNumber());

        if (!accountExists) {
            return ApiResponse.builder()
                    .status(Status.USER_DOES_NOT_EXIST.getStatusCode())
                    .message(Status.USER_DOES_NOT_EXIST.getStatusMessage())
                    .result(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(accountDetailsRequest.getAccountNumber());
        return ApiResponse.builder().
                status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(AccountDetails.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

}
