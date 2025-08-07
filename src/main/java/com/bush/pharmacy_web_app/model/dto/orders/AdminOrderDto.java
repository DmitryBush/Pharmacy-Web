package com.bush.pharmacy_web_app.model.dto.orders;

import com.bush.pharmacy_web_app.model.dto.user.AdminCustomerReadDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.PharmacyBranchReadDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record AdminOrderDto(Long id,
                            OrderStatusDto statusOrder,
                            Instant date,
                            AdminCustomerReadDto user,
                            PharmacyBranchReadDto branch,
                            List<OrderItemReadDto> cartItems,
                            BigDecimal result) {
}
