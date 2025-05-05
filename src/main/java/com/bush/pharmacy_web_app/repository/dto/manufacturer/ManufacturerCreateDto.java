package com.bush.pharmacy_web_app.repository.dto.manufacturer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ManufacturerCreateDto(Long id,
                                    @NotNull @NotBlank @Length(max = 64) String name,
                                    @NotNull @Valid CountryCreateDto country) {
}
