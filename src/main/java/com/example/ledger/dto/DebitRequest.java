package com.example.ledger.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DebitRequest {

    @NotNull(message="Amount is Required")
    @Positive(message="Amount should be greater than zero")
    private BigDecimal amount;

    @NotBlank(message="ReferenceId is not blank")
    private String referenceId;

    public BigDecimal getAmount(){
        return amount;
    }
    public String getReferenceId(){
        return referenceId;
    }
}
