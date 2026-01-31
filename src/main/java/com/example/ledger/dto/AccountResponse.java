package com.example.ledger.dto;

import java.time.Instant;

public class AccountResponse {

    private Long id;
    private String name;
    private Instant createdAt;

    public AccountResponse(){}

    public AccountResponse(Long id,String name,Instant createdAt){
        this.id=id;
        this.name=name;
        this.createdAt= createdAt;
    }
    public Long  getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Instant getCreatedAt(){
        return createdAt;
    }
}
