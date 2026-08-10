package com.kartly.domain;

public record OrderLine(String productName, Money unitPrice, int quantity) {
    public OrderLine{
        if(productName == null ||productName.isBlank()){
            throw new IllegalArgumentException("productName must not be blank");
        }
        if(unitPrice == null){
            throw new IllegalArgumentException("unitPrice must not be null");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
    public Money lineTotal(){
        return unitPrice.multiply(quantity);
    }
}
