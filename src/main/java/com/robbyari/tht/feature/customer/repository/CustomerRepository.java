package com.robbyari.tht.feature.customer.repository;

import com.robbyari.tht.feature.customer.dto.CustomerBalanceDTO;
import com.robbyari.tht.feature.customer.dto.CustomerTransactionDTO;
import com.robbyari.tht.feature.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query(value = """
            SELECT 
                a.account_type as accountType,
                a.account_number as accountNumber,
                t.amount as amount,
                t.transaction_type as transactionType,
                t.transaction_reference as transactionReference,
                t.transaction_date as transactionDate,
                c.name as customerName,
                c.email as customerEmail,
                c.phone_number as customerPhoneNumber,
                ta.account_number as targetAccountNumber,
                tc.name as targetCustomerName
            FROM transactions t
            INNER JOIN accounts a ON t.account_id = a.id
            INNER JOIN customers c ON a.customer_id = c.id
            LEFT JOIN accounts ta ON t.target_account_id = ta.id
            LEFT JOIN customers tc ON ta.customer_id = tc.id
            WHERE c.id = :customerId OR tc.id = :customerId
            ORDER BY t.transaction_date DESC
            """, nativeQuery = true)
    List<CustomerTransactionDTO> findTransactionsByCustomerId(@Param("customerId") Long customerId);
    
    @Query(value = """
            SELECT 
                c.id as customerId,
                c.name as customerName,
                c.email as customerEmail,
                SUM(a.balance) as balance
            FROM customers c
            JOIN accounts a ON c.id = a.customer_id
            GROUP BY c.id, c.name, c.email
            ORDER BY c.name ASC
            """, nativeQuery = true)
    List<CustomerBalanceDTO> getAllCustomerBalances();
}
