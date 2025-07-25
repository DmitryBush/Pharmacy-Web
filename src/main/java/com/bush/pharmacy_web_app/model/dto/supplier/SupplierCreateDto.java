package com.bush.pharmacy_web_app.model.dto.supplier;

import com.bush.pharmacy_web_app.model.dto.address.AddressCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SupplierCreateDto(@NotNull @NotBlank @Pattern(regexp = "^\\d{10,12}$") String itn,
                                @NotNull @NotBlank String name,
                                @NotNull @Valid AddressCreateDto address) {
}
