package com.kartly.service;

import com.kartly.domain.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountPolicy{
    private final BigDecimal percentage;
    public PercentageDiscount(BigDecimal percentage){
        if(percentage == null || percentage.signum() < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0){
            throw new IllegalArgumentException("percentage must be between 0 and 100.");
        }
        this.percentage = percentage;
    }

    @Override
    public Money apply(Money total){
        BigDecimal discountFraction = percentage.divide(BigDecimal.valueOf(100),4, RoundingMode.HALF_UP);
        BigDecimal discountAmount = total.amount().multiply(discountFraction);
        BigDecimal discountedTotal = total.amount().subtract(discountAmount);
        return new Money(discountedTotal, total.currency());
    }
}
