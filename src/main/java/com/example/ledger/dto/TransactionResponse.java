package com.example.ledger.dto;

import com.example.ledger.domain.ledger.LedgerEntryType;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionResponse {
    private final Long id;
    private final BigDecimal amount;
    private final LedgerEntryType type;
    private final String referenceId;
    private final Instant createdAt;

    public TransactionResponse(
            Long id,
            BigDecimal amount,
            LedgerEntryType type,
            String referenceId,
            Instant createdAt
    ){
        this.id=id;
        this.amount=amount;
        this.referenceId=referenceId;
        this.type=type;
        this.createdAt=createdAt;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LedgerEntryType getType() {
        return type;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
