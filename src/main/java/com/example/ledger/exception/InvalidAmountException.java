package com.example.ledger.exception;

import java.math.BigDecimal;

// 2. Separate file: InvalidAmountException.java
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(BigDecimal amount) {
        super("Amount must be positive. Received: " + amount);
    }
}
