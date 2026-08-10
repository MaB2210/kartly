package com.kartly.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount, String currency) {
    public Money{
        if (amount == null){
            throw new IllegalArgumentException("Amount nust not be null");
        }
        if(currency == null || currency.isBlank()){
            throw new IllegalArgumentException("Currency must not be blank.");
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(double amount, String currency){
        return new Money(BigDecimal.valueOf(amount), currency);
    }

    public Money add(Money other){
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money multiply(int quantity){
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)), this.currency);
    }

    private void validateSameCurrency(Money other){
        if(!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Cannot operate on other currencies: "+ this.currency + " vs " + other.currency);
        }
    }

}
