package com.bush.pharmacy_web_app.repository.dto.catalog;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SupplierCreateDto(@NotNull @NotBlank @Pattern(regexp = "^\\d{10,12}$") String itn,
                                @NotNull @NotBlank String name,
                                @NotNull @Valid AddressCreateDto address) {
}
