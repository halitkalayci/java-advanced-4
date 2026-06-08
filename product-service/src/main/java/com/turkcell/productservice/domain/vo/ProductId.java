package com.turkcell.productservice.domain.vo;

import java.util.Objects;
import java.util.UUID;

/**
 * Product aggregate'in kimliği. Çıplak UUID yerine kendi tipine sahip bir ID:
 * ProductId'nizi başka bir CustomerId/OrderId karıştırmanıza izin vermez.
 * Bir VO'dur. Immutable
 * tüm alanlar final
 * toString, hash -> oto oluşturulur
 */
public record ProductId(UUID value) {
    public ProductId {
        Objects.requireNonNull(value, "ProductId değeri null olamaz");
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(String raw)
    {
        return new ProductId(UUID.fromString(raw));
    }
}
