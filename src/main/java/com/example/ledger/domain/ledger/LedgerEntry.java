package com.example.ledger.domain.ledger;

import com.example.ledger.domain.account.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="ledger_entries",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id","reference_id"})
})
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id",nullable = false)
    private Account account;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LedgerEntryType type;

    @Column(name = "reference_id",nullable = false,length = 100)
    private String referenceId;

    @Column(name="created_at",nullable = false,updatable = false)
    private Instant createdAt;

    protected LedgerEntry(){}

    public LedgerEntry(
            Account account,
            BigDecimal amount,
            LedgerEntryType type,
            String referenceId){
        this.account=account;
        this.amount=amount;
        this.type=type;
        this.referenceId=referenceId;
        this.createdAt=Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Account getAccountId() {
        return account;
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