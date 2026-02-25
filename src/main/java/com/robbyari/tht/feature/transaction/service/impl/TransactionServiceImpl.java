package com.robbyari.tht.feature.transaction.service.impl;

import com.robbyari.tht.common.exception.BusinessException;
import com.robbyari.tht.feature.account.entity.Account;
import com.robbyari.tht.feature.account.repository.AccountRepository;
import com.robbyari.tht.feature.transaction.dto.TransactionRequest;
import com.robbyari.tht.feature.transaction.dto.TransactionResponse;
import com.robbyari.tht.feature.transaction.entity.Transaction;
import com.robbyari.tht.feature.transaction.repository.TransactionRepository;
import com.robbyari.tht.feature.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        log.info("Creating transaction for account ID: {}", request.getAccountId());

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Account not found"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionReference(UUID.randomUUID().toString());

        if ("WITHDRAWAL".equalsIgnoreCase(request.getTransactionType())) {
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        } else if ("TRANSFER".equalsIgnoreCase(request.getTransactionType())) {
            long targetAccountId = request.getTargetAccountId();
            if (targetAccountId <= 0) {
                 throw new BusinessException(HttpStatus.BAD_REQUEST, "Target account ID is required for transfer");
            }
            if (account.getId().equals(targetAccountId)) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Cannot transfer to the same account");
            }
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }

            Account targetAccount = accountRepository.findById(targetAccountId)
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Target account not found"));

            account.setBalance(account.getBalance().subtract(request.getAmount()));
            targetAccount.setBalance(targetAccount.getBalance().add(request.getAmount()));
            accountRepository.save(targetAccount);
            
            transaction.setTargetAccount(targetAccount);
        } else if ("DEPOSIT".equalsIgnoreCase(request.getTransactionType())) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        } else {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid transaction type");
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToTransactionResponse(savedTransaction);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionReference(transaction.getTransactionReference())
                .accountId(transaction.getAccount().getId())
                .accountNumber(transaction.getAccount().getAccountNumber())
                .targetAccountId(transaction.getTargetAccount() != null ? transaction.getTargetAccount().getId() : null)
                .targetAccountNumber(transaction.getTargetAccount() != null ? transaction.getTargetAccount().getAccountNumber() : null)
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}
