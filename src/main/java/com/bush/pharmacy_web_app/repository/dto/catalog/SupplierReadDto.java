package com.bush.pharmacy_web_app.repository.dto.catalog;

import com.bush.pharmacy_web_app.repository.dto.orders.AddressReadDto;

public record SupplierReadDto(String itn,
                              String name,
                              AddressReadDto address) {
}
