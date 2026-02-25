package com.robbyari.tht.feature.transaction.service;

import com.robbyari.tht.feature.transaction.dto.TransactionRequest;
import com.robbyari.tht.feature.transaction.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
}
