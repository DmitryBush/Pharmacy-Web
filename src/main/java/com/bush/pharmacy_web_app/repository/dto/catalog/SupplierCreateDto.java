package com.bush.pharmacy_web_app.repository.dto.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record SupplierCreateDto(@NotNull @NotBlank @Length(min = 10, max = 12) String itn,
                                @NotNull @NotBlank String name,
                                @NotNull AddressCreateDto address) {
}
