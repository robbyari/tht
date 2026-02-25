package com.robbyari.tht.feature.customer.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({
    "customerId",
    "customerName",
    "customerEmail",
    "balance"
})
public interface CustomerBalanceDTO {
    Long getCustomerId();
    String getCustomerName();
    String getCustomerEmail();
    BigDecimal getBalance();
}
