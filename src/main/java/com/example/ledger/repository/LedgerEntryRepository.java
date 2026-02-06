package com.example.ledger.repository;

import com.example.ledger.domain.ledger.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {
}
