package com.bush.pharmacy_web_app.model.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MedicineTypeDto(@NotNull @NotBlank @Length(min = 2, max = 64) String name,
                              String parent) {
}
