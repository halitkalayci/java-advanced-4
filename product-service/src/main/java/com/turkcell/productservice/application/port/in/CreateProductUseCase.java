package com.turkcell.productservice.application.port.in;

import com.turkcell.productservice.domain.vo.ProductId;

/***
 * Inbound Port (use case sözleşmesi). Dış dünya domain'e direkt bağlanıp aksiyon alamaz
 * bu arayüz üzerinden sunulduğu kadarıyla bir şeyleri tetikleyebilir.
 */
public interface CreateProductUseCase
{
    ProductId create(CreateProductCommand createProductCommand);

    /**
     * CQRS Command gibi. WEB DTOSU değil. Application'ın kendi giriş modeli.
     */
    record CreateProductCommand(String name, String priceAmount, String currencyCode) {}
}
// Benim domain işlemlerimin giriş noktası.