package com.bush.pharmacy_web_app.model.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductTypeMappingDto(@NotBlank String type,
                                    String parentType,
                                    @NotNull Boolean isMain) {
}
