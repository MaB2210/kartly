package com.kartly.service;

import com.kartly.domain.Money;

import java.math.BigDecimal;

public class FlatAmountDiscount implements DiscountPolicy{
    private final Money discountAmount;

    public FlatAmountDiscount(Money discountAmount){
        if(discountAmount == null){
            throw new IllegalArgumentException("discount must not be null");
        }
        this.discountAmount = discountAmount;
    }

    @Override
    public Money apply(Money total){
        if(discountAmount.amount().compareTo(total.amount()) >= 0){
            return new Money(BigDecimal.ZERO, total.currency());
        }
        return new Money(total.amount().subtract(discountAmount.amount()), total.currency());
    }
}
