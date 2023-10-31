package com.dot.ai.dtos.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public enum TransactionStatus {

    PENDING,
    SUCCESS,
    FAILED,
    INSUFFICIENT_FUNDS;
}
