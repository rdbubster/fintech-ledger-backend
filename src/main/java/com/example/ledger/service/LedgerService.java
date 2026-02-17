package com.example.ledger.service;

import com.example.ledger.domain.account.Account;
import com.example.ledger.domain.ledger.LedgerEntry;
import com.example.ledger.domain.ledger.LedgerEntryType;
import com.example.ledger.dto.TransactionResponse;
import com.example.ledger.exception.AccountNotFoundException;
import com.example.ledger.exception.InsufficientBalanceException;
import com.example.ledger.exception.InvalidAmountException;
import com.example.ledger.repository.AccountRepository;
import com.example.ledger.repository.LedgerEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerService(AccountRepository accountRepository,
                         LedgerEntryRepository ledgerEntryRepository) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Transactional
    public void credit(Long accountId, BigDecimal amount, String referenceId) {

        validateAmount(amount);

        // 1️. Idempotency check
        if (ledgerEntryRepository
                .existsByAccount_IdAndReferenceId(accountId, referenceId)) {
            return; // Safe retry
        }

        // 2️. Lock account
        Account account = accountRepository
                .findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // 3️. Insert ledger entry
        LedgerEntry entry = new LedgerEntry(
                account,
                amount,
                LedgerEntryType.CREDIT,
                referenceId
        );

        ledgerEntryRepository.save(entry);
    }

    @Transactional
    public void debit(Long accountId, BigDecimal amount, String referenceId) {

        validateAmount(amount);

        // 1️. Idempotency check
        if (ledgerEntryRepository
                .existsByAccount_IdAndReferenceId(accountId, referenceId)) {
            return; // Safe retry
        }

        // 2. Lock account
        Account account = (Account) accountRepository
                .findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // 3️. Calculate balance
        BigDecimal balance = ledgerEntryRepository.calculateBalance(accountId);

        // 4️. Enforce invariant
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(balance, amount);
        }

        // 5️. Insert ledger entry
        LedgerEntry entry = new LedgerEntry(
                account,
                amount,
                LedgerEntryType.DEBIT,
                referenceId
        );

        ledgerEntryRepository.save(entry);
    }



    public Page<TransactionResponse> getTransactions(
            Long accountId,
            int page,
            int size
    ){
        // validate if account exist

        if(!accountRepository.existsById(accountId)){
            throw new AccountNotFoundException(accountId);
        }

        //defined pagination and sorting

        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt"));

        // here we fetch from repo

        Page<LedgerEntry> ledgerPage=ledgerEntryRepository.findByAccount_Id(accountId,pageable);

        // here we map to DTO

        return ledgerPage.map(entry->
                new TransactionResponse(
                        entry.getId(),
                        entry.getAmount(),
                        entry.getType(),
                        entry.getReferenceId(),
                        entry.getCreatedAt()
                ));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }
    }


}

