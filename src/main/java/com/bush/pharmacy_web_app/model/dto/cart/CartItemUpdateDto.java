package com.bush.pharmacy_web_app.model.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemUpdateDto(@NotNull Long productId,
                                @NotNull @Positive Integer quantity) {
}
