package com.robbyari.tht.feature.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    @Min(value = 1, message = "Customer ID must be greater than 0")
    private long customerId;

    @NotNull(message = "Initial balance cannot be null")
    @DecimalMin(value = "0.0", message = "Initial balance must be non-negative")
    private BigDecimal initialBalance;

    @NotNull(message = "Account type cannot be null")
    @Pattern(regexp = "SAVINGS|CHECKING", message = "Account type must be SAVINGS or CHECKING")
    private String accountType; // SAVINGS, CHECKING
}
