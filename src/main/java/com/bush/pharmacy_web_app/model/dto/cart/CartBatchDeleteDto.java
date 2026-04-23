package com.bush.pharmacy_web_app.model.dto.cart;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartBatchDeleteDto(@NotNull List<Long> productIdList) {
}
