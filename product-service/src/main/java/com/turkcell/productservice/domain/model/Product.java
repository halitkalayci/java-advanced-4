package com.turkcell.productservice.domain.model;

import com.turkcell.productservice.domain.vo.Money;
import com.turkcell.productservice.domain.vo.ProductId;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Product - Bir entity
 * POJO => Plain old java object
 * Bir kimliği var, özellikleri var ama asla dışarıya bağımlı değil.
 * import java.* dışında bir şey olmaması lazım
 * JAVA diliyle gelen built-in özellikler dışında bir şey olmaması gerek.
 * Framework'ten bağımsızdır. Ne spring ne jpa importu içerir. (Clean Arch. - Dependency Rule)
 * Dışarıya setter VERMEZ - durumlar yalnızca iş kuralı içeren domain methodlarıyla değişir.
 * changePrice(), rename(), deactivate(), reactivate()
 * constructor -> private olmalıdır
*/
public class Product
{
    private ProductId id; // immutable
    private String name;
    private boolean active;
    private Money price;
    // Money, Stok Kodu


    private Product(ProductId id,String name, boolean active, Money price) {
        this.id = id;
        this.name=name;
        this.active=active;
        this.price = price;
    }

    // Factory Method(lar)
    public static Product create(String name, Money price)
    {
        requireValidName(name);
        return new Product(ProductId.generate() ,name, true, price);
    }

    private static void requireValidName(String name)
    {
        if(name==null || name.isBlank())
            throw new IllegalArgumentException("Ürün adı boş olamaz.");

        if(name.length() > 200)
            throw new IllegalArgumentException("Ürün adı 200 karakterden uzun olamaz.");
    }


    public String name() { return name; }

    public boolean isActive() { return active; }

}
