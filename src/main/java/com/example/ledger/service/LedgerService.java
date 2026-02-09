package com.example.ledger.service;

import com.example.ledger.domain.account.Account;
import com.example.ledger.domain.ledger.LedgerEntry;
import com.example.ledger.domain.ledger.LedgerEntryType;
import com.example.ledger.repository.AccountRepository;
import com.example.ledger.repository.LedgerEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerService(AccountRepository accountRepository,LedgerEntryRepository ledgerEntryRepository){
        this.accountRepository=accountRepository;
        this.ledgerEntryRepository=ledgerEntryRepository;
    }

    @Transactional
    public void credit(Long accountId, BigDecimal amount,String referenceId){

        if(amount==null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Credit amount must be greater than zero");
        }

        Account account=accountRepository.findById(accountId).orElseThrow(()->new IllegalArgumentException("Account not found"));

        LedgerEntry entry=new LedgerEntry(account,amount, LedgerEntryType.CREDIT,referenceId);

        ledgerEntryRepository.save(entry);
    }
    @Transactional
    public void debit(Long accountId,BigDecimal amount, String referenceId){
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Debit amount must be greater than zero");
        }
        // here we locked the account row

        Account account=accountRepository.findById(accountId).orElseThrow(()-> new IllegalArgumentException("Account not found"));


        // here we calculate the balance

        BigDecimal balance= ledgerEntryRepository.calculateBalance(accountId);


        // check the invariant

        if(balance.compareTo(amount)<0){
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Here we insert DEBIT ledger entry

        LedgerEntry entry =new LedgerEntry(
                account,amount,LedgerEntryType.DEBIT,referenceId
        );
        ledgerEntryRepository.save(entry);
    }

}
