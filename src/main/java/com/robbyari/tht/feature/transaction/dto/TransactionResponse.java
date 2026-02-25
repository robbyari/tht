package com.robbyari.tht.feature.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private String transactionReference;
    private Long accountId;
    private String accountNumber;
    private Long targetAccountId;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String transactionType;
    private String description;
    private LocalDateTime transactionDate;
}
