package com.bush.pharmacy_web_app.model.dto.cart;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;

public record CartItemReadDto(ProductPreviewReadDto medicine,
                              Integer amount) {
}
