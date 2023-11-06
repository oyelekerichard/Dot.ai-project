package com.dot.ai.dtos.response;

import java.math.BigDecimal;

public interface TransactionResponse {

     Long id();
     String transactionReference();
     BigDecimal amount();
     Double transactionFee();
     String billedAmount();
     String description();
     String transactionStatus();
     Boolean commissionWorthy();
     Double commission();
     String userId();

}
