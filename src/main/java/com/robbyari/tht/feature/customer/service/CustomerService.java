package com.robbyari.tht.feature.customer.service;

import com.robbyari.tht.feature.customer.dto.CustomerRequest;
import com.robbyari.tht.feature.customer.dto.CustomerBalanceDTO;
import com.robbyari.tht.feature.customer.dto.CustomerResponse;
import com.robbyari.tht.feature.customer.dto.CustomerTransactionDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    List<CustomerTransactionDTO> getCustomerTransactions(long customerId);
    List<CustomerBalanceDTO> getAllCustomerBalances();
}
