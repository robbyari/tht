package com.robbyari.tht.feature.account.service.impl;

import com.robbyari.tht.common.exception.BusinessException;
import com.robbyari.tht.feature.account.dto.AccountRequest;
import com.robbyari.tht.feature.account.dto.AccountResponse;
import com.robbyari.tht.feature.account.entity.Account;
import com.robbyari.tht.feature.account.repository.AccountRepository;
import com.robbyari.tht.feature.account.service.AccountService;
import com.robbyari.tht.feature.customer.entity.Customer;
import com.robbyari.tht.feature.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        log.info("Creating account for customer ID: {}", request.getCustomerId());

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Customer not found"));

        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(request.getInitialBalance());
        account.setAccountType(request.getAccountType());
        account.setStatus("ACTIVE");

        String accountNumber;
        do {
            accountNumber = UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        account.setAccountNumber(accountNumber);

        Account savedAccount = accountRepository.save(account);

        return mapToAccountResponse(savedAccount);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .customerId(account.getCustomer().getId())
                .customerName(account.getCustomer().getName())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
