package com.robbyari.tht.feature.customer.service.impl;

import com.robbyari.tht.common.exception.BusinessException;
import com.robbyari.tht.feature.customer.dto.CustomerBalanceDTO;
import com.robbyari.tht.feature.customer.dto.CustomerRequest;
import com.robbyari.tht.feature.customer.dto.CustomerResponse;
import com.robbyari.tht.feature.customer.dto.CustomerTransactionDTO;
import com.robbyari.tht.feature.customer.entity.Customer;
import com.robbyari.tht.feature.customer.repository.CustomerRepository;
import com.robbyari.tht.feature.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating customer: {}", request.getEmail());

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email already exists: " + request.getEmail());
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());

        Customer savedCustomer = customerRepository.save(customer);

        return mapToCustomerResponse(savedCustomer);
    }

    @Override
    public List<CustomerTransactionDTO> getCustomerTransactions(long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId);
        }
        
        return customerRepository.findTransactionsByCustomerId(customerId);
    }

    @Override
    public List<CustomerBalanceDTO> getAllCustomerBalances() {
        return customerRepository.getAllCustomerBalances();
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
