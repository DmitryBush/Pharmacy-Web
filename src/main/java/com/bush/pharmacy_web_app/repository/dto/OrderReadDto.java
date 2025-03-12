package com.bush.pharmacy_web_app.repository.dto;

import java.time.Instant;
import java.util.List;

public record OrderReadDto(Long id,
                           Short statusOrder,
                           Instant date,
                           PharmacyBranchReadDto branch,
                           List<CartItemsReadDto> cartItems) {
}
