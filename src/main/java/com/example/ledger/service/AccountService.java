package com.example.ledger.service;
import com.example.ledger.domain.account.Account;
import com.example.ledger.dto.AccountResponse;
import com.example.ledger.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
        );
    }
}

