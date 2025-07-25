package com.bush.pharmacy_web_app.model.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MedicineTypeDto(@NotNull @NotBlank @Length(max = 64) String name,
                              @NotNull String parent) {
}
