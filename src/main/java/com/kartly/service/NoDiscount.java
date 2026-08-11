package com.kartly.service;

import com.kartly.domain.Money;

public class NoDiscount implements DiscountPolicy{
    @Override
    public Money apply(Money total) {
        return total;
    }
}
