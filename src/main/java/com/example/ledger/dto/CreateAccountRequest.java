package com.example.ledger.dto;

public class CreateAccountRequest {

    private String name;

    public CreateAccountRequest(){}

    public CreateAccountRequest(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
