package com.bush.pharmacy_web_app.repository.dto.medicine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ProductCategoryDto(@Valid @NotNull MedicineTypeDto type,
                                 @NotNull Boolean isMain) {
}
