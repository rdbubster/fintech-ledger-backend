package com.example.ledger.domain.account;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name="created_at", nullable = false,updatable = false)
    private Instant createdAt;

    protected Account(){
    }
    public Account(String name){
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Account name must not be null or blank");
        }
        this.name=name;
        this.createdAt=Instant.now();
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Instant getCreatedAt(){
        return createdAt;
    }
}
