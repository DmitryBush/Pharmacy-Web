package com.bush.pharmacy_web_app.model.dto.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CartUpdateDto(@Valid @NotNull CartItemUpdateDto item) {
}
