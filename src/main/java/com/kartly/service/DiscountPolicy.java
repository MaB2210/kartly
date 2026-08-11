package com.kartly.service;

import com.kartly.domain.Money;

public interface DiscountPolicy {
    Money apply(Money total);
}
