package com.turkcell.productservice.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
       @NotBlank @Size(max=200) String name,
       @NotBlank String price,
       @NotBlank @Size(min=3, max=3) String currency
) {
}
