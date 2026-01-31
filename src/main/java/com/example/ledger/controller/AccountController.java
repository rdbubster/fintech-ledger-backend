package com.example.ledger.controller;

import com.example.ledger.dto.AccountResponse;
import com.example.ledger.dto.CreateAccountRequest;
import com.example.ledger.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
}

