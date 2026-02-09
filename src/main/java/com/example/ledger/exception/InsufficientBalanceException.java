package com.example.ledger.exception;

import java.math.BigDecimal;

// 3. Separate file: InsufficientBalanceException.java
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal balance, BigDecimal requested) {
        super("Insufficient funds. Available: " + balance + ", Requested: " + requested);
    }
}
