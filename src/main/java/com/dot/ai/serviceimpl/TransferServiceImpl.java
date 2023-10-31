package com.dot.ai.serviceimpl;

import com.dot.ai.configs.Utility;
import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.EmailDetails;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.AccountDetails;
import com.dot.ai.dtos.response.ApiResponse;
import com.dot.ai.entites.Transaction;
import com.dot.ai.entites.User;
import com.dot.ai.enums.Status;
import com.dot.ai.enums.TransactionStatus;
import com.dot.ai.repositories.TransactionRepository;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.EmailService;
import com.dot.ai.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public ApiResponse creditAccount(CreditDebitRequest request) {
//      check if the account exists or not

        Boolean accountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!accountExists) {
            return ApiResponse.builder()
                    .status(Status.USER_DOES_NOT_EXIST.getStatusCode())
                    .message(Status.USER_DOES_NOT_EXIST.getStatusMessage())
                    .result(null)
                    .build();
        }

        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());

        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

//        save updated account balance to the database to update the balance od the account
        userRepository.save(userToCredit);

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .description("Transfer of " + request.getAmount() + " was made to " + userToCredit.getAccountBalance())
                .transactionStatus(TransactionStatus.SUCCESSFUL.toString())
                .transactionReference(Utility.generateTransactionRef())
                .userId(userToCredit.getEmail())
                .commissionWorthy(false)
                .build();

        transactionRepository.save(transaction);

        return ApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(AccountDetails.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public ApiResponse debitAccount(CreditDebitRequest request) {

        String transRef = Utility.generateTransactionRef();

//        check if the account exists
//        we will also have to cheeck if the amount to withdraw is not more than the amount in the account
        Boolean accountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!accountExists) {
            return ApiResponse.builder()
                    .status(Status.USER_DOES_NOT_EXIST.getStatusCode())
                    .message(Status.USER_DOES_NOT_EXIST.getStatusMessage())
                    .result(null)
                    .build();
        }
        log.info("we are here");
        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

//        do the second check to see if the balance is not less than the amount we want to debit
        log.info("checking the error emanatiing from here");
        log.info("checking the error emanatiing from here 11 :: " + userToDebit.getAccountBalance());
        log.info("checking the error emanatiing from here :: " + userToDebit.getAccountBalance().toString());

        int availableBalance = userToDebit.getAccountBalance().intValue();

        int amountToDebit = request.getAmount().intValue();

        if (availableBalance < amountToDebit) {

            Transaction transaction = Transaction.builder()
                    .amount(request.getAmount())
                    .description("Transfer Not Successful")
                    .transactionStatus(TransactionStatus.INSUFFICIENT_FUND.toString())
                    .transactionReference(Utility.generateTransactionRef())
                    .userId(userToDebit.getEmail())
                    .commissionWorthy(false)
                    .build();

            transactionRepository.save(transaction);

            return ApiResponse.builder()
                    .status(Status.INSUFFICIENT_BALANCE.getStatusCode())
                    .message(Status.INSUFFICIENT_BALANCE.getStatusMessage())
                    .result(null)
                    .build();
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));

            log.info("ACCOUNT BALANCE DIFFERENCE :: " + ((request.getAmount()).intValue()));
            userRepository.save(userToDebit);

            Transaction transaction = Transaction.builder()
                    .amount(request.getAmount())
                    .description("Transfer  Successful")
                    .transactionStatus(TransactionStatus.SUCCESSFUL.toString())
                    .transactionReference(transRef)
                    .userId(userToDebit.getEmail())
                    .commissionWorthy(false)
                    .build();

            transactionRepository.save(transaction);

            return ApiResponse.builder()
                    .status(Status.SUCCESS.getStatusCode())
                    .message(Status.SUCCESS.getStatusMessage())
                    .result(AccountDetails.builder()
                            .accountNumber(userToDebit.getAccountNumber())
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                            .build())
                    .build();
        }
    }

    @Override
    public ApiResponse transfer(TransferRequest transferRequest) {

        String transRef = Utility.generateTransactionRef();
        Double txnFee = 0.00;
        Double commisionFee = 0.00;
//        get the account to debit and check if it exists or not
        Boolean isDestinationAccountExist = userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());

        if (!isDestinationAccountExist) {
            return ApiResponse.builder()
                    .status(Status.USER_DOES_NOT_EXIST.getStatusCode())
                    .message(Status.USER_DOES_NOT_EXIST.getStatusMessage())
                    .result(null)
                    .build();
        }
        User sourceAccountUser = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());

        if (transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {

            transactionRepository.save(Transaction.builder()
                    .amount(transferRequest.getAmount())
                    .description("Transfer Not Successful")
                    .transactionStatus(TransactionStatus.INSUFFICIENT_FUND.toString())
                    .transactionReference(Utility.generateTransactionRef())
                    .userId(sourceAccountUser.getEmail())
                    .commissionWorthy(false)
                    .build());

            return ApiResponse.builder()
                    .status(Status.INSUFFICIENT_BALANCE.getStatusCode())
                    .message(Status.INSUFFICIENT_BALANCE.getStatusMessage())
                    .result(null)
                    .build();

        }
        txnFee = Utility.calculateTransactionFee(transferRequest.getAmount().doubleValue());
        commisionFee = Utility.commissionFee(txnFee);

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));

        userRepository.save(sourceAccountUser);

        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Debit Alert")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been deducted from yuour account, your current balance is now " + sourceAccountUser.getAccountBalance())
                .build();

        transactionRepository.save(Transaction.builder()
                .amount(transferRequest.getAmount())
                .description(" Debit on " + sourceAccountUser.getAccountNumber() + " to :: " + transferRequest.getDestinationAccountNumber())
                .transactionStatus(TransactionStatus.SUCCESSFUL.toString())
                .transactionReference(transRef)
                .userId(sourceAccountUser.getEmail())
                .transactionFee(txnFee)
                .commission(0.00)
                .commissionWorthy(false)
                .build());

        emailService.sendEmailAlerts(debitAlert);

        User destinationAccountUser = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));

        userRepository.save(destinationAccountUser);

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Credit Alert")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been sent to your account from " + transferRequest.getSourceAccountNumber() + ", your current balance is now " + destinationAccountUser.getAccountBalance())
                .build();

        transactionRepository.save(Transaction.builder()
                .amount(transferRequest.getAmount())
                .description("Transfer Successful :: Credit on " + destinationAccountUser.getAccountNumber() + " from :: " + transferRequest.getSourceAccountNumber())
                .transactionStatus(TransactionStatus.SUCCESSFUL.toString())
                .transactionReference(transRef)
                .transactionFee(0.00)
                .commission(0.00)
                .userId(sourceAccountUser.getEmail())
                .commissionWorthy(false)
                .build());

        emailService.sendEmailAlerts(creditAlert);

        return ApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(transRef)
                .build();
    }

    @Override
    public ApiResponse getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return ApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(transactions)
                .build();
    }
}
