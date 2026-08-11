package com.kartly.domain;

public record Customer(String id, String name, String email, Address defaultAddress) {
    public Customer{
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("id must not be blank");
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name must not be blank");
        }
        if(email == null || !email.contains("@")){
            throw new IllegalArgumentException("email must be valid.");
        }
        if(defaultAddress == null){
            throw new IllegalArgumentException("defaultAddress must not be null");
        }
    }
}
