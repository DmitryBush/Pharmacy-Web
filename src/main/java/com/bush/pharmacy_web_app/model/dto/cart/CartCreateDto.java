package com.bush.pharmacy_web_app.model.dto.cart;

import com.bush.pharmacy_web_app.model.dto.user.CustomerReadDto;

import java.util.List;

public record CartCreateDto(CustomerReadDto user,
                            List<CartItemCreateDto> cartItemsList) {
}
