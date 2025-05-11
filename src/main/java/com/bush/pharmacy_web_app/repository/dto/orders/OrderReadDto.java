package com.bush.pharmacy_web_app.repository.dto.orders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderReadDto(Long id,
                           String statusOrder,
                           Instant date,
                           PharmacyBranchReadDto branch,
                           List<OrderItemReadDto> cartItems,
                           BigDecimal result) {
}
