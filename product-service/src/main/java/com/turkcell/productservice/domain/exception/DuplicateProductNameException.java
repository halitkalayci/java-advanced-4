package com.turkcell.productservice.domain.exception;

/***
 * Domain Hatası: Aynı isimde ürün zaten var.
 */
public class DuplicateProductNameException extends RuntimeException {
    public DuplicateProductNameException(String name) {
        super("Bu isimde bir ürün zaten mevcut: " + name);
    }
}
