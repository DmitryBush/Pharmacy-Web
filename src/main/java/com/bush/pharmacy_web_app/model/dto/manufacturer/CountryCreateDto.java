package com.bush.pharmacy_web_app.model.dto.manufacturer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CountryCreateDto(@NotNull @NotBlank @Length(max = 50) String country) {
}
