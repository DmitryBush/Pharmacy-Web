package com.bush.pharmacy_web_app.repository.dto.orders;

import java.util.List;

public record CartReadDto(List<CartItemsReadDto> cartItems) {
}
