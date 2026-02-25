package com.robbyari.tht.feature.account.service;

import com.robbyari.tht.feature.account.dto.AccountRequest;
import com.robbyari.tht.feature.account.dto.AccountResponse;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
}
