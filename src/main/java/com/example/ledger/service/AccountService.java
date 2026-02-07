package com.example.ledger.service;
import com.example.ledger.domain.account.Account;
import com.example.ledger.dto.AccountResponse;
import com.example.ledger.repository.AccountRepository;
import com.example.ledger.repository.LedgerEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public AccountService(AccountRepository accountRepository,LedgerEntryRepository ledgerEntryRepository) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository=ledgerEntryRepository;
    }

    public AccountResponse createAccount(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }

        Account account = new Account(name);
        Account saved = accountRepository.save(account);

        return new AccountResponse(
                saved.getId(),
                saved.getName(),
                saved.getCreatedAt()
        );
    }

    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCreatedAt()
        );}

        public BigDecimal getBalance(Long accountId) {

            if (!accountRepository.existsById(accountId)) {
                throw new IllegalArgumentException("Account not found");
            }
            return ledgerEntryRepository.calculateBalance(accountId);


        }
}

