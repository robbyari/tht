package com.robbyari.tht.feature.transaction.controller;

import com.robbyari.tht.common.dto.ApiResponse;
import com.robbyari.tht.feature.transaction.dto.TransactionRequest;
import com.robbyari.tht.feature.transaction.dto.TransactionResponse;
import com.robbyari.tht.feature.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@RequestBody @Valid TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.createTransaction(request);
        ApiResponse<TransactionResponse> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Transaction created successfully",
                transactionResponse
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
