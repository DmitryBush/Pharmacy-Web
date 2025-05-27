package com.bush.pharmacy_web_app.repository.dto.cart;

import java.util.List;

public record CartReadDto(List<CartItemReadDto> cartItems) {
}
