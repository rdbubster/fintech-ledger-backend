package com.example.ledger.exception;

// 1. Separate file: AccountNotFoundException.java
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account not found: " + id);
    }
}

