package com.example.ledger.controller;

import com.example.ledger.dto.AccountResponse;
import com.example.ledger.dto.CreateAccountRequest;
import com.example.ledger.dto.CreditRequest;
import com.example.ledger.dto.DebitRequest;
import com.example.ledger.service.AccountService;
import com.example.ledger.service.LedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final LedgerService ledgerService;

    public AccountController(AccountService accountService,LedgerService ledgerService) {
        this.accountService = accountService;
        this.ledgerService = ledgerService;
    }

    // POST /accounts
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody CreateAccountRequest request
    ) {
        AccountResponse response =
                accountService.createAccount(request.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET /accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable Long id
    ) {
        AccountResponse response =
                accountService.getAccountById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<?> creditAccount(
            @PathVariable Long id,@RequestBody CreditRequest request
    ) {
        try {
            ledgerService.credit(id, request.getAmount(), request.getReferenceId()
            );
            return ResponseEntity.noContent().build();
        }
// here it is the updated one of credit part we included ? and we had try and catch block. catch ke baad se it has been updated

        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @PathVariable Long id
    ){
        BigDecimal balance=accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }


    @PostMapping("/{id}/debit")
    public ResponseEntity<?> debitAccount(
            @PathVariable Long id,@RequestBody DebitRequest request) {

        try {
            ledgerService.debit(id, request.getAmount(), request.getReferenceId());
            return ResponseEntity.noContent().build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

