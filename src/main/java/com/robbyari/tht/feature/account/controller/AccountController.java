package com.robbyari.tht.feature.account.controller;

import com.robbyari.tht.common.dto.ApiResponse;
import com.robbyari.tht.feature.account.dto.AccountRequest;
import com.robbyari.tht.feature.account.dto.AccountResponse;
import com.robbyari.tht.feature.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody @Valid AccountRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        ApiResponse<AccountResponse> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Account created successfully",
                accountResponse
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
