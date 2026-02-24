package com.example.ledger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class CreditRequest {

    @NotNull(message="Amount is required")
    @Positive(message="Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message="ReferenceId must not be blanked")
    private String referenceId;



    public BigDecimal getAmount(){
        return amount;
    }
    public String getReferenceId(){
        return referenceId;
    }
}
