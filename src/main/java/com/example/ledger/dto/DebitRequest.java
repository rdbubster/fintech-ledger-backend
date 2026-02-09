package com.example.ledger.dto;

import java.math.BigDecimal;

public class DebitRequest {
    private BigDecimal amount;
    private String referenceId;

    public BigDecimal getAmount(){
        return amount;
    }
    public String getReferenceId(){
        return referenceId;
    }
}
