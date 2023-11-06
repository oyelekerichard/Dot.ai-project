package com.dot.ai.serviceimpl;

import com.dot.ai.configs.Utility;
import com.dot.ai.dtos.request.CreditDebitRequest;
import com.dot.ai.dtos.request.EmailDetails;
import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.AccountDetails;
import com.dot.ai.dtos.response.DotApiResponse;
import com.dot.ai.dtos.response.TransactionSummary;
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
import org.springframework.stereotype.Service;

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
    public DotApiResponse transfer(TransferRequest transferRequest) {

        String transRef = Utility.generateTransactionRef();
        Double txnFee = 0.00;
        Double commisionFee = 0.00;
//        get the account to debit and check if it exists or not
        Boolean isDestinationAccountExist = userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());

        if (!isDestinationAccountExist) {
            return DotApiResponse.builder()
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

            return DotApiResponse.builder()
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

        return DotApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(transRef)
                .build();
    }

    @Override
    public DotApiResponse getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return DotApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(transactions)
                .build();
    }

    @Override
    public DotApiResponse getAllTransactions(String status, String userId, String startDate, String endDate) {
        return null;
    }

    @Override
    public DotApiResponse getTransactionSummary(String startDate, String endDate) {

        List<TransactionSummary> transactions = transactionRepository.getTransactionSummary(startDate, endDate);
        return DotApiResponse.builder()
                .status(Status.SUCCESS.getStatusCode())
                .message(Status.SUCCESS.getStatusMessage())
                .result(transactions)
                .build();
    }
}
