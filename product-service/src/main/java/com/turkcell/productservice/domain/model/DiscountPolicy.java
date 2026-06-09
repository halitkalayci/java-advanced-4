package com.turkcell.productservice.domain.model;

import com.turkcell.productservice.domain.vo.Money;

public interface DiscountPolicy
{
    Money discountFor(Money price);
}
