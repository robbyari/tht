package com.robbyari.tht.feature.customer.controller;

import com.robbyari.tht.common.dto.ApiResponse;
import com.robbyari.tht.feature.customer.dto.CustomerBalanceDTO;
import com.robbyari.tht.feature.customer.dto.CustomerRequest;
import com.robbyari.tht.feature.customer.dto.CustomerResponse;
import com.robbyari.tht.feature.customer.dto.CustomerTransactionDTO;
import com.robbyari.tht.feature.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse customerResponse = customerService.createCustomer(request);
        ApiResponse<CustomerResponse> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                customerResponse
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/transactions")
    public ResponseEntity<ApiResponse<List<CustomerTransactionDTO>>> getCustomerTransactions(@PathVariable long customerId) {
        List<CustomerTransactionDTO> transactions = customerService.getCustomerTransactions(customerId);
        ApiResponse<List<CustomerTransactionDTO>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Customer transactions retrieved successfully",
                transactions
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/balances")
    public ResponseEntity<ApiResponse<List<CustomerBalanceDTO>>> getAllCustomerBalances() {
        List<CustomerBalanceDTO> balances = customerService.getAllCustomerBalances();
        ApiResponse<List<CustomerBalanceDTO>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Customer balances retrieved successfully",
                balances
        );
        return ResponseEntity.ok(response);
    }
}
