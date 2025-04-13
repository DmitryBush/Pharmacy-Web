package com.bush.pharmacy_web_app.repository.dto.manufacturer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ManufacturerCreateDto(Long id,
                                    @NotNull @NotBlank @Length(max = 25) String name,
                                    @NotNull CountryCreateDto country) {
}
