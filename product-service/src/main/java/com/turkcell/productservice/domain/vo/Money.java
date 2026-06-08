package com.turkcell.productservice.domain.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Para'nın VO'su. Tutar ve para birimi TEK pakette taşınması.
 * aritmetik ve para birimi uyumu kuralları burada yaşar.
 */
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "Amount null olamaz");
        Objects.requireNonNull(currency, "Currency null olamaz");
        if(amount.signum() < 0)
            throw new IllegalArgumentException("Para tutarı negatif olamaz");

        // 10.0 = 10.00 == (false)
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public static Money of(String amount, String currencyCode) {
        return new Money(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

    public Money add(Money other)
    {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    private void requireSameCurrency(Money other)
    {
        if(!currency.equals(other.currency))
            throw new IllegalArgumentException("Para birimleri uyuşmuyor.");
    }
}
