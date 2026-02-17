package com.example.ledger.repository;
import com.example.ledger.domain.ledger.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {

    @Query("""
SELECT COALESCE(SUM(
CASE
WHEN le.type='CREDIT' THEN le.amount
WHEN le.type='DEBIT' THEN -le.amount
END
),0)
FROM LedgerEntry le
WHERE le.account.id= :accountId
""")
    BigDecimal calculateBalance(@Param("accountId") Long accountId);

    boolean existsByAccount_IdAndReferenceId(Long accountId, String referenceId);

    Page<LedgerEntry> findByAccount_Id(Long accountId, Pageable pageable);
}
