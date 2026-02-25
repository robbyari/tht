package com.robbyari.tht.feature.customer.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonPropertyOrder({
    "accountType",
    "accountNumber",
    "amount",
    "transactionType",
    "transactionReference",
    "transactionDate",
    "customerName",
    "customerEmail",
    "customerPhoneNumber",
    "targetAccountNumber",
    "targetCustomerName"
})
public interface CustomerTransactionDTO {
    String getAccountType();
    String getAccountNumber();
    BigDecimal getAmount();
    String getTransactionType();
    String getTransactionReference();
    LocalDateTime getTransactionDate();
    String getCustomerName();
    String getCustomerEmail();
    String getCustomerPhoneNumber();
    String getTargetAccountNumber();
    String getTargetCustomerName();
}
